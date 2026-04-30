package com.migueldk17.breeze.ui.features.historico.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.migueldk17.breeze.domain.MovimentacaoDomain
import com.migueldk17.breeze.domain.model.BreezeInsight
import com.migueldk17.breeze.enums.TipoMovimentacao
import com.migueldk17.breeze.ui.features.historico.ui.ComparativoFiltro
import com.migueldk17.breeze.ui.features.historico.ui.TipoDeDados
import com.migueldk17.breeze.ui.features.historico.ui.comparativo.ComparativoData
import com.migueldk17.breeze.ui.features.historico.ui.comparativo.model.ComparativoModel
import com.migueldk17.breeze.ui.utils.formatarValorEmReal
import com.migueldk17.breeze.uistate.UiState
import com.migueldk17.breeze.usecases.GetCategoryTotalByMonthUseCase
import com.migueldk17.breeze.usecases.GetInsightMensalUseCase
import com.migueldk17.breeze.usecases.GetMovimentacoesDoDiaUseCase
import com.migueldk17.breeze.usecases.GetMovimentacoesDoMesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class HistoricoComparativoViewModel @Inject constructor(
    private val getMovimentacoesDoMesUseCase: GetMovimentacoesDoMesUseCase,
    private val getMovimentacoesDoDiaUseCase: GetMovimentacoesDoDiaUseCase,
    private val getCategoryTotalByMonthUseCase: GetCategoryTotalByMonthUseCase,
    private val getInsightMensalUseCase: GetInsightMensalUseCase
): ViewModel() {

    // 1. O ESTADO DE CONTROLE (Single Source of Truth)
    // O filtro é o "volante" da tela. Sempre que ele muda, a tela inteira recalcula o que precisa exibir.
    private val _filtro = MutableStateFlow(ComparativoFiltro())

    // Controla o visual dos botões/abas na UI (qual está selecionado: Dia, Mês ou Categoria)
    private val _comparativoModel = MutableStateFlow(ComparativoModel())
    val comparativoModel = _comparativoModel.asStateFlow()

    // 2. A MEMÓRIA DO APP
    // Guarda o último mês visitado. Essencial para quando o usuário está olhando o "Dia 15"
    // e aperta o botão de voltar: o app sabe exatamente para qual mês retornar.
    private val _mesBackup = MutableStateFlow("")
    val mes = _mesBackup.asStateFlow()

    // 3. O REATOR PRINCIPAL DA UI (Pipeline de Dados)
    // Ouve todas as mudanças de `_filtro` e reage instantaneamente.
    val uiState: StateFlow<UiState<ComparativoData>> = _filtro
        .flatMapLatest { filtro ->
            val data = filtro.data.orEmpty()
            // Switch case inteligente: Chama o UseCase correto dependendo da aba selecionada.
            // O flatMapLatest garante que se o usuário clicar rápido em 3 abas,
            // só a busca da última aba será finalizada, economizando bateria e processamento.
            when (filtro.tipoDeDados) {
                TipoDeDados.MES -> getMovimentacoesDoMesUseCase(data).map { processaMovimentacoes(it) }
                TipoDeDados.DIA -> getMovimentacoesDoDiaUseCase(data).map { processaMovimentacoes(it) }
                TipoDeDados.CATEGORIA -> getCategoryTotalByMonthUseCase(data).map { ComparativoData.Categoria(it) }
            }
        }
        .map { data ->
            // Valida se as listas estão vazias para mostrar a tela com a Nina na rede (UiState.Empty)
            val isEmpty = when (data) {
                is ComparativoData.Movimentacoes -> data.list.isEmpty()
                is ComparativoData.Categoria -> data.list.isEmpty()
            }
            if (isEmpty) {
                UiState.Empty
            } else {
                UiState.Success(data)
            }
        }
        .catch { emit(UiState.Error(it.message ?: "Erro desconhecido")) } // Blindagem contra crashes de banco
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000), // Mantém vivo por 5s ao rotacionar a tela
            initialValue = UiState.Loading
        )

    // 4. O MOTOR DE INSIGHTS (A Dica da Nina)
    // Ouve exclusivamente as mudanças de mês para calcular a porcentagem de economia.
    val insightDoMes: StateFlow<BreezeInsight?> = mes
        .filter { it.isNotBlank() && it.contains("-")}
        .flatMapLatest { mesSelecionado ->
            // Higienização de dados: O dropLast(1) remove o '%' usado pelo SQL (ex: "2026-04%" vira "2026-04")
            // garantindo que o YearMonth.parse do UseCase funcione perfeitamente.
            getInsightMensalUseCase(mesSelecionado.dropLast(1))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    // =========================================================================
    // INTENTS DA UI (Ações disparadas pelo usuário na View)
    // =========================================================================

    fun setMes (mes: String) {
        _mesBackup.value = mes // Salva na memória
        _filtro.update { it.copy(data = mes, tipoDeDados = TipoDeDados.MES) }
        _comparativoModel.update { it.copy(tipoDeDados = TipoDeDados.MES) }
    }

    fun setDia(dia: String) {
        _filtro.update { it.copy(data = dia, tipoDeDados = TipoDeDados.DIA) }
        _comparativoModel.update { it.copy(tipoDeDados = TipoDeDados.DIA) }
    }

    // A Mágica do Backup: Resgata o mês da memória sem o usuário precisar digitar ou selecionar de novo
    fun voltarParaMes() {
        _filtro.update { it.copy(data = _mesBackup.value, tipoDeDados = TipoDeDados.MES) }
        _comparativoModel.update { it.copy(tipoDeDados = TipoDeDados.MES) }
    }

    fun setCategoria() {
        _filtro.update { it.copy(tipoDeDados = TipoDeDados.CATEGORIA) }
        _comparativoModel.update { it.copy(tipoDeDados = TipoDeDados.CATEGORIA) }
    }

    // =========================================================================
    // HELPER METHODS (Lógica de Negócio do ViewModel)
    // =========================================================================

    /**
     * Pega uma lista bruta de movimentações e separa o que é Receita e Despesa.
     * Já calcula o saldo e empacota tudo formatado como texto (String) para a UI apenas exibir.
     */
    private fun processaMovimentacoes(list: List<MovimentacaoDomain>): ComparativoData {
        val (entradas, saidas) = list.partition { it.tipo == TipoMovimentacao.ENTRADA }
        val totalEntradas = entradas.sumOf { it.valor }
        val totalSaidas = saidas.sumOf { it.valor }

        return ComparativoData.Movimentacoes(
            list = list,
            totalReceitas = totalEntradas.formatarValorEmReal(),
            totalDespesas = totalSaidas.formatarValorEmReal(),
            saldoFinal = (totalEntradas + totalSaidas).formatarValorEmReal()
        )
    }
}