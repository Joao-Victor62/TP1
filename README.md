# Trabalhos-praticos-AED3
Trabalhos feitos na diciplina de Algoritmos e Estruturas de Dados III 
# TP1
# TP2
    O código no repositório se trata de um programa de "catálogo de streaming",
    onde é possível registrar séries, seus episódios, atores, e suas respectivas atuações 
    em séries. O menu inicial é composto de dois menus menores: O de séries e o
    de atores. No menu de atores é possível registrar as entidades ator, buscar, excluir, 
    editar e buscar a quais séries estão vinculados. 
    No menu de séries, é possível registrar novas entidades, buscá-las, excluí-las, editá-las
    vincular episódios à elas e gerenciar os elencos, vinculando atores e dando nome aos seus
    personagens. 

    Integrantes do grupo: 
    Matheus Campello
    João Victor Pena
    Luiz 

    Classes adicionadas:
        - Models:
            * Ator: Modelo da entidade ator, contendo seu nome e id;
            * Atuação: Modelo que registra a relação de ator e série, com id próprio, 
                       id do ator, id da série e nome do papel na respectiva série;
        -Arquivos:
                * ArquivoAtor: Gerencia arquivo com registros de todos os atores.
                * ArquivoAtuacao: Gerencia arquivo com registro de todos as atuações,
                                  e registra as relações serie-ator através de duas árvores
                                  B+ que permitem buscas.
        - Menus:
            * MenuAtorPrincipal: Acessado pelo menu principal. Contém o CRUD de atores, e
                                 o método buscarSerieAtor(), que mostra as séries em que um 
                                 ator desempenha um papel.
            * MenuAtorSerie:     Acessado através do menu de séries, na opção
                                 "gerenciar elenco". Possui toda a gestão de link entre
                                 series e atores. Suas funções principais são um CRUD de
                                 entidades Atuação, e com através da classe ArquivoAtuacao,
                                 registram em duas árvores B+ pares IdAtorIdAtuacao e 
                                 IdSerieIdAtuacao, possibilitando a busca de atores que
                                 atuam na série escolhida, ou as séries que o ator escolhido
                                 desempenha um papel.

    Dificuldades: Em geral, o desenvolvimento ocorreu sem muitas dificuldades, salvo uma
                  ocorrência mais ao fim. Devido a uma discrepância entre a instanciação da 
                  entidade Atuação (o contrutor pedia o Id da série primeiro e do ator depois,
                  mas ao instancia-la, acidentalmente estavamos fazendo o inverso), os registros
                  da árvore de IdAtorIdAtuacao e IdSerieIdAtuacao ficaram trocados entre si, ou seja,
                  os registros de uma foram para a outra e vice-versa. A detecção desse erro e sua
                  subsequente correção demoraram uma grande quantidade de tempo, mas felizmente,
                  foi possível resolver o problema. No fim, todos os resultados foram alcançados,
                  e o grupo, de maneira geral, está satisfeito com o que foi alcaçado.
    
    
    As operações de inclusão, busca, alteração e exclusão de atores estão implementadas e funcionando corretamente?
    O relacionamento entre séries e atores foi implementado com árvores B+ e funciona corretamente, assegurando a 
    consistência entre as duas entidades? SIM
    É possível consultar quais são os atores de uma série? SIM
    É posssível consultar quais são as séries de um ator? SIM
    A remoção de séries remove os seus vínculos de atores? SIM
    A inclusão de um ator em uma série em um episódio se limita aos atores existentes? SIM
    A remoção de um ator checa se há alguma série vinculado a ele? SIM
    O trabalho está funcionando corretamente? SIM
    O trabalho está completo? SIM
    O trabalho é original e não a cópia de um trabalho de outro grupo? SIM