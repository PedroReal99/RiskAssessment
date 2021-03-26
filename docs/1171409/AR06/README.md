** [1171409](../)** - AR06
=======================================


# 1. Requisitos

+ AR06. Como AR pretendo registar a existência de uma envolvente (e.g. Refinaria da Petrogal) de um determinado tipo (e.g. Local Inflamável) numa determinada localização (e.g. Av. da Liberdade, Leça da Palmeira).
    
        AR06.1. Com base na informação introduzida, o sistema deve obter a localização GPS do mesmo e solicitar a sua confirmação e/ou retificação.
        
        AR06.2. Pelo menos uma imagem aérea centrada na envolvente registada deve ser exibida ao AR.
    
    
        Nota: As envolventes registadas pelos AR servem essencialmente para, por um lado, suprir limitações dos próprios serviços externos de georreferenciação na identificação/reconhecimento de certos tipos de envolventes; e, por outro lado, garantir que estas envolventes são tidas em consideração no calculo de índice de risco para locais (objetos seguros) na sua proximidade.


A interpretação feita deste requisito foi no sentido de ,dado  a morada do local ,o tipo de envolvente, o nome do envolvente,obter as coordenadas GPS(localização) e posterioremente registar a existencia de um meio envolvente. 


=====================================================================

# 2. Análise

*Neste secção o estudante deve relatar o estudo/análise/comparação que fez com o intuito de tomar as melhores opções de design para a funcionalidade bem como aplicar diagramas/artefactos de análise adequados.*

• Este caso de uso terá de ser implementado no módulo RISK ANALYSIS pois trata-se de uma user story do Analista de Risco. No entanto, vai interagir com o módulo CORE pois requer regras de negócio que estão neste implementadas e com o EXTERNAL_GEOREFERENCE_SERVICE a fim de obter as coordenadas GPS(localização).
• O objetivo deste caso de uso é registar a existencia de um tipo envolvente,dada uma morada, o nome do envolvente e o seu tipo,adicionando também,ao registo, a localização do mesmo(Coordenadas GPS) e da visualização da imagem aerea do mesmo.
• Para isto, o Analista de Risco,AR, terá que interagir com o sistema, enviando os parametros para o funcionamento do mesmo, e é solicitada uma confirmação dos dados introduzidos.

=====================================================================

# 3. Design

+ Para este caso de uso necessitamos de uma morada,do tipo e do nome do meio envolvente.
+ Precisamos de verificar se o surrounding existe na base de dados, se nao existir aguardar até ao fim do caso de uso para persistir.
+ Obter localização atraves da api predifinida
+ Obter imagem aérea do local dada a latitude e a longitude 
+ pedir confirmação e após verificação dos dados pelo AR guardar os dados 
+ mostrar imagem aerea  do meio envolvente

## 3.1. Realização da Funcionalidade

*Nesta secção deve apresentar e descrever o fluxo/sequência que permite realizar a funcionalidade.*

## 3.2. Diagrama de Classes

![AR06_SD](ARO6_SD.svg)

## 3.3. Padrões Aplicados

+ Strategy
+ Builder
+ DTO

## 3.4. Testes 
 
 + Para este UC, o teste principal é testar se a conexção é feita de forma correta ou nao. Os restantes testes apenas são para  testar a invocação dos metodos até ao metodo principal:
 
   
        @Test
        public void testShowAerealImagemFromLocation() throws IOException {
            System.out.println("testShowAerealImagemFromLocation");
            SurroundingController instance = new SurroundingController();
            String address = "Alameda Prof.Hernâni Monteiro,4200-319 Porto";
            boolean res= instance.showAerealImagemFromLocation(address);
            assertEquals(true,res);
        }
 
 
 
        @Test
        public void testObtainImageByLocation() throws IOException {
            System.out.println("testObtainImageByLocation");
            ExternalGeoRefServiceController instance = new ExternalGeoRefServiceController();
            String address = "Alameda Prof. Hernâni Monteiro,4200-319 Porto";
            boolean res= instance.showImageFromLocationWithAPI(address);
            assertEquals(true,res);
        }
  
        
        @Test
        public void testObtainImageByLocation() throws Exception {
            System.out.println("testObtainImageByLocation");
            ExternalGeoRefService instance = new ExternalGeoRefService();
            boolean res = instance.obtainImageByLocation("Alameda Prof. Hernâni Monteiro,4200-319 Porto");
            assertEquals(true, res);
        }
    
    + Teste para verificar que a transição do Modulo EXTERNAL_GEOREFERENCE_SERVICE através do DTO é bem executada:     
        
        
   
            /**
            * Test of obtainSurroundingByLocation method, of class
            * RegisterSurroundingByLocation.
            * @throws java.lang.Exception
            */
            @Test
            public void testObtainSurroundingByLocation() throws Exception {
                System.out.println("obtainSurroundingByLocation");
        
                String category = "Hospital";
                String stName = "Hostital Sao Joao";
                String address = "Alameda Prof. Hernâni Monteiro,4200-319 Porto";
        
                GPSLocation gps = new GPSLocation(41.183052F, -8.6009655F, 115.754036F, "Porto");
                RegisterSurroundingByLocation instance = new RegisterSurroundingByLocation();
                Surrounding result = instance.obtainSurroundingByLocation(category, stName, address);
        
                assertEquals(stName, result.obtainSName().toString());
                assertEquals(category, result.obtainSTName().toString());        
                assertEquals(gps.getLatitude(), result.obtainLocation().getLatitude(),0.01);
                assertEquals(gps.getLongitude(), result.obtainLocation().getLongitude(),0.01);
                assertEquals(gps.getAltitude(), result.obtainLocation().getAltitude(),0.01);
                assertEquals(gps.getAltitude(), result.obtainLocation().getAltitude(),0.01);
            }
        
=====================================================================

# 4. Implementação

*Nesta secção o estudante deve providenciar, se necessário, algumas evidências de que a implementação está em conformidade com o design efetuado. Para além disso, deve mencionar/descrever a existência de outros ficheiros (e.g. de configuração) relevantes e destacar commits relevantes;*

+ Para este UC foi criada uma UI para facilitar a intercação entre o analista de risco e o sistema.
+ para tal o sistema solicita o nome, o tipo e a morada do envolvente a registar ,verifica na base de dados a sua existencia, caso nao exista é criado. 
+ o dados inseridos sofrem uma validação e posteriormente uma confirmação por parte do analista de risco.
caso os dados estejam corretos, uma imagem aerea do tipo envolvente é mostrada , dando opção de registar mais envolventes.
+ a imagem e aberta com o browser, o metodo obtainImageByLocation faz a conceção ao Desktop como podemos comprovar :

        
        public boolean obtainImageByLocation(String adress) throws IOException {
            String addressFormated = Param.createParam("google", "staticview", adress);
            Strategy query_url = Request.loadSourceUrl("google", "staticview");
            String url = Request.obtainFullUrl(query_url, "google", addressFormated, "staticview");

            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                try {

                //this is the only way that convert special characters into ascii to open the browser with the uri because with special caracters the method doesn't work
                    URI parsedUri = parseURIToAsciiNormalized(url);
                    desktop.browse(parsedUri);

                } catch (IOException | URISyntaxException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                } catch (Exception ex) {
                    ex.printStackTrace();

                }
            } else {
                Runtime runtime = Runtime.getRuntime();
                try {
                    runtime.exec("xdg-open " + url);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return true;
        }

        public URI parseURIToAsciiNormalized(String url) throws URISyntaxException {
                URI uri = new URI(url);
                String parsedUri = uri.toASCIIString();
                return new URI(parsedUri);
        }


=====================================================================

# 5. Integration/Demonstration

*Nesta secção o estudante deve descrever os esforços realizados no sentido de integrar a funcionalidade desenvolvida com as restantes funcionalidades do sistema.*

+ para este UC, houve uma necessidade de compreender como funcionava a  api da static view a fim de obter a imagem aerea e posteriormente como realizar a conexão via Desktop.

=====================================================================

# 6. Observações

*Nesta secção sugere-se que o estudante apresente uma perspetiva critica sobre o trabalho desenvolvido apontando, por exemplo, outras alternativas e ou trabalhos futuros relacionados.*

+ Esta semana o trabalho correu como tinha planeado, onde tudo foi implementado respeitando as regras de negocio, uso de padrões, modularidade e simplicidade na realização das tarefas.
+ para este caso de uso apenas se poderia, a meu ver, melhorar a apresentação da UI.

