package br.res.automatizado.tests.Suite;

import br.res.automatizado.Configuracao.BaseTest;
import br.res.automatizado.tests.AuthTest;
import br.res.automatizado.tests.ContasTest;
import br.res.automatizado.tests.MovimentacaoTest;
import br.res.automatizado.tests.SaldoTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(org.junit.runners.Suite.class)
@Suite.SuiteClasses({
        ContasTest.class,
        MovimentacaoTest.class,
        SaldoTest.class,
        AuthTest.class
})
public class SuiteTest extends BaseTest {
}
