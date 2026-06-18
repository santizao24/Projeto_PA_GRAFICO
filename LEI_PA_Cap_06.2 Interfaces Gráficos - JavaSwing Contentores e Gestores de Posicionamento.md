

ProgramaçãoAplicada
## Interfaces Gráficos
Contentores e Gestores de Posicionamento
## Marco Veloso
marco.veloso@estgoh.ipc.pt
(Revistoe adaptadoa partirdo original de António José Mendes e Maria José Marcelino)

Interfaces Gráficos –Contentores e Gestores de Posicionamento
Marco Veloso –Programação Aplicada
## 2
ElementosGráficos,AWTeApplets
InterfaceGráficos
ComponentesdeumInterfaceGráfico
GestãodeEventos
ContentoreseGestoresdePosicionamento
## Interacçãocomoutilizador
## Caixasdediálogoemenus
ElementosMultimédia
ListagenseTabelas
## Formataçõesgráficas
## Agenda

Interfaces Gráficos –Contentores e Gestores de Posicionamento
Marco Veloso –Programação Aplicada
## 3
Contentores e Gestores de Posicionamento
Contentores e Gestores de Posicionamento

Interfaces Gráficos –Contentores e Gestores de Posicionamento
Marco Veloso –Programação Aplicada
## 4
## Exemplocompleto:
publicclassBotaoextendsJFrame{
//Construtor
publicBotao(){
## //1obtémcontentorprincipal
Containercontentor=getContentPane();
## //2defineadisposição
contentor.setLayout(newFlowLayout());
## //3acriaelementográfico
JButtonb1=newJButton(“BotaoUm”);
## //3badicionaelementográficoaocontentorprincipal
contentor.add(b1);
## //...
## }
## //...
## }
Interface gráfico -Exemplo

Interfaces Gráficos –Contentores e Gestores de Posicionamento
Marco Veloso –Programação Aplicada
## 5
## Umcontentor(container)éumtipoespecialdecomponenteque
serveparaagruparoutroscomponentes(eventualmenteoutro
contentor)
Porexemplo,umJPaneléumcontentore,porisso,podem-lheser
adicionadosoutroscomponentes
## Acadacontentoréadicionadoumgestordeposicionamento
## (layoutmanager)quecontrolaaformacomoosseuscomponentes
sãomostrados
## Cadacontentortemdoismétodos:
–setLayout(...)-Indicaqualogestordeposicionamentoausar
–add(...)-Adicionaumcomponenteaocontentor
## Contentores

Interfaces Gráficos –Contentores e Gestores de Posicionamento
Marco Veloso –Programação Aplicada
## 6
Algunscontentores(JPaneleJApplet)têmqueserligadosauma
superfíciegráfica(e.g.umaappletéligadaaumbrowserouaumapplet
viewer),nãosendoautónomos
Outroscontentores(JWindow,JFrame,JDialog)podemsermovidos
independentemente,sãoautónomos,recebendocomoargumentootextoa
apresentarnofrisosuperior
JFrameframe=newJFrame(“JanelaPrincipal”);
## Autilizaçãodecontentoresimplicaqueoscomponentessejamprimeiro
adicionadosaocontentor,sendoestedepoisadicionadoàframe(janela
principal,usualmenteumaimplementaçãoJFrame)
## Assim,umainterfacegráficaéconseguidapelajunçãodecontentorese
outroscomponentes
## Odesenhodainterfacegráficaéumapartefundamentaldodesenvolvimento
deaplicações-nãoesquecerqueparaumutilizadorcomumainterfaceéo
sistema
## Contentores

Interfaces Gráficos –Contentores e Gestores de Posicionamento
Marco Veloso –Programação Aplicada
## 7
## Contentoresnãoautónomos:
## Contentoresautónomos:
ClasseFunção
JWindow
é uma janelaautónoma ‘top-level’ sem ‘borders’e sem menu
JFrame
é uma janelaautónoma ‘top-level’ com um título e um ‘border’
JDialog
é uma janelaautónoma com o propósito de apresentar
mensagens(informação, erro, etc.) ao utilizador
ClasseFunção
JPanel
é o contentor mais simples, associado a outros contentores. Um
JPanelé um espaço para colocar outros componentes,
incluindo outros painéis
JApplet
contentor que necessita de um Applet Viewerpara ser executado
## Contentores

Interfaces Gráficos –Contentores e Gestores de Posicionamento
Marco Veloso –Programação Aplicada
## 8
## Exemplo:
//Naappletouframe
JButtonbotao1=newJButton("1");
JButtonbotao2=newJButton("2");
JPanelp=newJPanel();
p.add(botao1);
p.add(botao2);
Containercont=getContentPane();
cont.add(p);
## 12
## Botão 1
## Botão 2
## Painelp
## Frame
Aplicação de elementos num contentor

Interfaces Gráficos –Contentores e Gestores de Posicionamento
Marco Veloso –Programação Aplicada
## 9
Oselementosgráficos(componentes:JComponent)maiscomunsnuma
interfacegráficasãoosseguintes:
-Etiquetas(JLabel);
-Camposdetexto(JTextFieldeJPasswordField);
-Áreasdetexto(JTextArea);
-Comboboxes(JComboBox);
-Botões(JButtoneJChoice);
-Botõesdeselecção(JCheckBox);
-Gruposdebotõesderádio(JRadioButtoneButtonGroup);
-Listas(JList);
-Tabelas(JTable);
-Potenciómetros(JScrollBareJSlider).
Nota:todasascomponentessãosubclasses(herdam)deJComponent
http://download.oracle.com/javase/tutorial/uiswing/components/index.html
http://java.sun.com/docs/books/tutorial/uiswing/components/index.html
http://java.sun.com/docs/books/tutorial/uiswing/examples/components/index.html
Elementos gráficos

Interfaces Gráficos –Contentores e Gestores de Posicionamento
Marco Veloso –Programação Aplicada
## 10
## Umgestordeposicionamento(layoutmanager)éumobjecto
quedecidecomodistribuiroscomponentesnumcontentor
## Cadacontentortemumgestordeposicionamentopordefeito
(asJDialogseJFramespossuemcomogestordeposicionamentopor
defeitooBorderLayoutenquantoasAppletePanelpossuemcomo
gestordeposicionamentopordefeitooFlowLayout)
## Háseisgestoresdeposicionamentopré-definidos:
–FlowLayout
–BorderLayout
–GridLayout
–GridBagLayout
–CardLayout
–BoxLayout
Gestores de Posicionamento

Interfaces Gráficos –Contentores e Gestores de Posicionamento
Marco Veloso –Programação Aplicada
## 11
## Layout
## Função
FlowLayout
‘Layout’de defeito para JPanels; pressupõe que os objectos sejam
colocados de forma centrada, da esquerda para a direita, sequencialmente
e em linhas sucessivas.
BorderLayout
‘Layout’de defeito para todos os ‘ContentPanes’(JDialogseJFrames);
divide o contentor em cinco áreas: ‘North’, ‘South’, ‘West’, ‘East’e ‘Center’;
o tamanho relativo das várias áreas depende do tamanho dos objectos
colocados em cada uma; o programador especifica em qual das áreas
pretende cada componente.
GridLayout
objectossão colocados numa grelha com um determinado número de
linhas e colunas, ocupando cada um uma célula da grelha; todas as
células têm a mesma dimensão.
GridBagLayout
grelha bidimensional com células de tamanhos diferentes em que os
componentes podem ocupar várias linhas ou colunas.
CardLayout
objectos são colocados de forma sobreposta, mas só um está visível em
cada instante; pode-se controlar qual o componente visível.
BoxLayout
posiciona os objectos numa única linha ou coluna. As dimensões de cada
objecto são respeitadas, e é possível definir qual o alinhamento dos
objectos.
Gestores de Posicionamento

Interfaces Gráficos –Contentores e Gestores de Posicionamento
Marco Veloso –Programação Aplicada
## 12
## Paraalterarotipodegestordeposicionamentopodeser
utilizadoométodosetLayout(...)
## Se,porexemplo,secolocarainstrução
setLayout(newGridLayout(2,2))
noconstrutordocontentor,oseugestordeposicionamento
passaráaseroGridLayout(grelhacomcélulasdeigual
tamanho)com2linhaspor2colunas.
Gestores de Posicionamento

Interfaces Gráficos –Contentores e Gestores de Posicionamento
Marco Veloso –Programação Aplicada
## 13
## Alternativamenteépossívelposicionarosobjectosem
localizaçõesfixas(‘layout’absoluto).
## Paraisso,olayoutédefinidocomonulledepoisdese
adicionaroobjectoao‘contentpane’usa-seométodo
setBounds()paraposicionaroobjectonalocalização
pretendida
nãoéaconselhadoparaaplicaçõesquepretendamser
independentesdaplataforma
Gestores de Posicionamento

Interfaces Gráficos –Contentores e Gestores de Posicionamento
Marco Veloso –Programação Aplicada
## 14
FlowLayout(http://java.sun.com/docs/books/tutorial/uiswing/layout/flow.html)
–PordefeitoparaAppletePanel
–Oscomponentessãocentrados
–Oscomponentessãocolocadosdaesquerdaparaadireita,
passandoparaalinhaseguintequandojánãocoubernalinha
corrente
–Épossívelalteraroalinhamentoatravésdaspropriedades:
LEFT,CENTEReRIGHT:
JpanelmyPanel=newJPanel(newFlowLayout(FlowLayout.LEFT))
Gestores de Posicionamento

Interfaces Gráficos –Contentores e Gestores de Posicionamento
Marco Veloso –Programação Aplicada
## 15
BorderLayout(http://java.sun.com/docs/books/tutorial/uiswing/layout/border.html)
–Temcincolocaisparacolocarcomponentes:
North,South,West,EasteCenter;
–Pordefeitoparatodosos‘ContentPanes’(JDialogseJFrames);
–Oprogramadorespecificaemqualdasáreasquercadacomponente
e.g.:contentor.add(myPanel,BorderLayout.NORTH);
–Otamanhorelativodasáreasédefinidopelotamanhodos
componentesquelásãocolocados
## North
## South
WestEastCenter
Gestores de Posicionamento

Interfaces Gráficos –Contentores e Gestores de Posicionamento
Marco Veloso –Programação Aplicada
## 16
GridLayout(http://java.sun.com/docs/books/tutorial/uiswing/layout/grid.html)
–Oscomponentessãocolocadosnumagrelhacomum
determinadonúmerodelinhasecolunas
–Aoadicionarcadacomponenteépossívelespecificaraposição
pretendida,casocontráriovaiserdispostonaprimeiracélulalivre
–Cadacomponenteécolocadonumacélula
–Todasascélulastêmamesmadimensão
–Épossívelespecificaroespaçamentohorizontaleverticalentreas
células,atravésdosmétodossetHgap()esetVgap()
Gestores de Posicionamento

Interfaces Gráficos –Contentores e Gestores de Posicionamento
Marco Veloso –Programação Aplicada
## 17
GridBagLayout(http://java.sun.com/docs/books/tutorial/uiswing/layout/gridbag.html)
–Umagrelhabidimensionaldelinhasecolunas
–Nemtodasascélulassãodomesmotamanho
–Oscomponentespodemocuparváriaslinhasoucolunas
–Cadacomponenteéassociadocomumconjuntoderestrições
Gestores de Posicionamento

Interfaces Gráficos –Contentores e Gestores de Posicionamento
Marco Veloso –Programação Aplicada
## 18
CardLayout(http://java.sun.com/docs/books/tutorial/uiswing/layout/card.html)
–Oscomponentessãocolocadosunsemcimadosoutros
–Apenasumcomponenteestávisívelemcadamomento
–Osmétodospodemcontrolarqualdoscomponentesécolocado
visível(atravésdométodosetVisible()oushow())
Gestores de Posicionamento

Interfaces Gráficos –Contentores e Gestores de Posicionamento
Marco Veloso –Programação Aplicada
## 19
BoxLayout(http://java.sun.com/docs/books/tutorial/uiswing/layout/box.html)
–Posicionaosobjectosnumaúnicalinha(variávelX_AXIS)ou
coluna(variávelY_AXIS)(podemaindaserdefinidaadisposiçãopor
LINE_AXISouPAGE_AXIS)
–Asdimensõesdecadaobjectosãorespeitadas,eépossíveldefinir
qualoalinhamentodosobjectos
Gestores de Posicionamento

Interfaces Gráficos –Contentores e Gestores de Posicionamento
Marco Veloso –Programação Aplicada
## 20
Gestores de Posicionamento -Exemplo

Interfaces Gráficos –Contentores e Gestores de Posicionamento
Marco Veloso –Programação Aplicada
## 21
cont
(Container, BorderLayout)
painelBotoes
(JPanel, FlowLayout)
painelCabecalho
(JPanel, GridLayout(3,1))
painelDados
(JPanel, FlowLayout)
painelNomeIdade
(JPanel, FlowLayout)
Gestores de Posicionamento -Exemplo

Interfaces Gráficos –Contentores e Gestores de Posicionamento
Marco Veloso –Programação Aplicada
## 22
importjava.awt.*;
importjavax.swing.*;
publicclassFormularioextendsJFrame
## {
publicFormulario()
## {
## //painelcomoscamposnomeeidade
JPanelpainelNomeIdade=
newJPanel(newFlowLayout());
painelNomeIdade.add(
newJLabel("Nome:",SwingConstants.RIGHT));
JTextFieldtextoNome=newJTextField(15);
painelNomeIdade.add(textoNome);
painelNomeIdade.add(
newJLabel("Idade:,SwingConstants.RIGHT"));
JTextFieldtextoIdade=newJTextField(2);
painelNomeIdade.add(textoIdade);
//painelcomoscamposBIesexo
JPanelpainelDados=newJPanel(newFlowLayout());
painelDados.add(
newJLabel("BI:",SwingConstants.RIGHT));
JTextFieldtextoBI=newJTextField(9);
painelDados.add(textoBI);
JRadioButtonmasc=newJRadioButton("M",false);
JRadioButtonfem=newJRadioButton("F",true);
ButtonGroupSexo=newButtonGroup();
sexo.add(masc);
sexo.add(fem);
painelDados.add(newJLabel("Sexo:"));
painelDados.add(masc);
painelDados.add(fem);
//comp.denomeIdade,dadosetitulo,
## //grelhade3line1col.
JPanelpainelCabecalho=
newJPanel(newGridLayout(3,1));
painelCabecalho.add(newJLabel(“RegistoEstudante",
(int)CENTER_ALIGNMENT));
painelCabecalho.add(painelNomeIdade);
painelCabecalho.add(painelDados);
## //painelcomostrêsbotões
JPanelpainelBotoes=newJPanel(newFlowLayout());
JButtonbotaoAnterior=newJButton("<<anterior");
painelBotoes.add(botaoAnterior);
JButtonbotaoContactos=newJButton("contactos");
painelBotoes.add(botaoContactos);
JButtonbotaoSeguinte=newJButton("seguinte>>");
painelBotoes.add(botaoSeguinte);
//colocaçãodopainéisnoContentPanedaapplet
Containercont=getContentPane();//1
cont.setLayout(newBorderLayout());//2
cont.add(painelCabecalho,"North");//3
cont.add(newJTextArea(),"Center");
cont.add(painelBotoes,"South");
## }
## }
Gestores de Posicionamento -Exemplo

Interfaces Gráficos –Contentores e Gestores de Posicionamento
Marco Veloso –Programação Aplicada
## 23
## Emresumo,paraconstruirainterfacegráfica:
–(1)Declararoscomponenteseosseuslisteners
–(2)Instanciareinicializarcomponenteselisteners
–(3)Adicionaroscomponentesaosseuscontentores(sehouver)
–(4)Adicionaroscomponentes(e/oucontentores)àframe
–(5)Registaroslistenersnosrespectivoscomponentes
## Paratratareventos:
–Escrevermétodosnaframeparacadatipodeacçãoqueo
utilizadorpossaefectuar
–Escreverumaclasse(ouclasses)paracadatipodelistener,
incluindoosmétodosrespectivos
Interface Gráfico e Tratamento de Eventos

Interfaces Gráficos –Contentores e Gestores de Posicionamento
Marco Veloso –Programação Aplicada
## 24
Transição de painéis
Transição de painéis

Interfaces Gráficos –Contentores e Gestores de Posicionamento
Marco Veloso –Programação Aplicada
## 25
## Seumainterfacefordesenhadaparaapresentardiferentes
componentesnumaáreacomum(JPanel,deacordocomas
acçõesdoutilizador),semprequehouvernecessidadedealteraro
layoutdaárea,deve-se:
## 1–removerascomponentesanterioresatravésdométodo
remove();
## 2–adicionarasnovascomponentesatravésdométodosadd()
## (2.1senecessárioactivaravisibilidadedacomponenteatravésdo
métodosetVisible(true));
## 3–invocarométodorevalidate();
## 4–invocarométodorepaint();
Transição de painéis

Interfaces Gráficos –Contentores e Gestores de Posicionamento
Marco Veloso –Programação Aplicada
## 26
## Ométodorepaint()éinvocadosemprequeumacomponente
necessitadese“redesenhar”,usualmenteemsituaçõesde
limpeza(cleanup)deecrã(usualmenteéinvocadoautomaticamente
quandoofocusouadimensãodajanelasãoalterados)
## Ométodorevalidate()(equivaleàconjunçãodosmétodos
invalidate()evalidate())deveserinvocadosempreque
componentessejamremovidos,ounovoscomponentes
adicionados
## Estainvocaçãoreinicializaalistagemdecomponentesactivos,
sendoumaindicaçãoparaumalimpeza(repaint)dasregiões
consideradas‘sujas’(dirtyregions),ouseja,quesofreramalterações
## Emalternativaaométodorevalidate()tambémpodeserinvocadoo
métodoupdateUI()
Transição de painéis

Interfaces Gráficos –Contentores e Gestores de Posicionamento
Marco Veloso –Programação Aplicada
## 27
Aáreadetransiçãodeveráserconstituídaporumpainel(JPanel)
principal
## Aformamaiseficienteserácriarnovospainéiscomosvárioslayouts
pretendidos,queserãoospainéisauxiliares
## Semprequehouverumatransiçãodelayout,removem-setodosos
painéisauxiliaresdopainelprincipal,eadiciona-seopainel
auxiliarpretendido(quedeveservisível)
## Sónofinaldesteprocedimentoseinvocamosmétodosrepaint()e
revalidate()
Transição de painéis

Interfaces Gráficos –Contentores e Gestores de Posicionamento
Marco Veloso –Programação Aplicada
## 28
publicclassMyClassextendsJFrameimplementsActionListener{
JPanelpainelPrincipal,painelAuxiliar1,painelAuxiliar2;
publicMyClass(){
## //...
painelPrincipal=newJPanel();
painelAuxiliar1=newJPanel();
painelAuxiliar2=newJPanel();
getContentPane().add(painelPrincipal,BorderLayout.NORTH);
## }
publicvoidactionPerformed(ActionEvente){
//if(e.getActionCommand().equals("TextobotaobuttonAccao")){
if(e.getSource().equals(buttonAccao)){
JButtonbutton1=newJButton();
## //...
painelAuxiliar1=newJPanel();
painelAuxiliar1.add(button1);
## //...
painelPrincipal.remove(painelAuxiliar2);
painelPrincipal.add(painelAuxiliar1);
painelPrincipal.repaint();
painelPrincipal.revalidate();
## }else{
## //...
## }
## }
## }
Transição de painéis

Interfaces Gráficos –Contentores e Gestores de Posicionamento
Marco Veloso –Programação Aplicada
## 29
publicclassMyClassextendsJFrameimplementsActionListener{
JPanelpainelPrincipal,painelAuxiliar1,painelAuxiliar2;
publicMyClass(){
## //...
painelPrincipal=newJPanel();
painelAuxiliar1=newJPanel();
painelAuxiliar2=newJPanel();
getContentPane().add(painelPrincipal,BorderLayout.NORTH);
## }
publicvoidactionPerformed(ActionEvente){
//if(e.getActionCommand().equals("TextobotaobuttonAccao")){
if(e.getSource().equals(buttonAccao)){
## //...
## }else{
JButtonbutton2=newJButton();
## //...
painelAuxiliar2=newJPanel();
painelAuxiliar2.add(button2);
## //...
painelPrincipal.remove(painelAuxiliar1);
painelPrincipal.add(painelAuxiliar2);
painelPrincipal.repaint();
painelPrincipal.revalidate();
## }
## }
## }
Transição de painéis

Interfaces Gráficos –Contentores e Gestores de Posicionamento
Marco Veloso –Programação Aplicada
## 30
## Aoinvésdesereutilizaromesmopainel,comosucedenoexemplo
anterior,omaiscorrectoécriarnovasclassesdepaineis(JPanel)
oujanelas(JFrame)queserãotrocadas(swapped)apartirda
classeprincipal.
## Atransiçãoentreestasjanelasérealizadaatravésdométodo
setVisible(boolean),quetornavisível(true)ouinvisível(false)uma
janela
Transição de painéis

Interfaces Gráficos –Contentores e Gestores de Posicionamento
Marco Veloso –Programação Aplicada
## 31
Applets (JApplet) e Aplicações (JFrame, JWindow)
## Appletse Aplicações

Interfaces Gráficos –Contentores e Gestores de Posicionamento
Marco Veloso –Programação Aplicada
## 32
JásabemosqueasJAppletsnãopodemfuncionar
autonomamente.Somentedentrodeumbrowseroudeumapplet
viewer
## Omesmonãosepassacomasaplicações,quesãoautónomas
AsaplicaçõesdevemserderivadasdaclasseJFrame
## Devempossuirmétodomain()emvezdeinit()ondededevecriar
ajanela(JFrame)correspondenteeiniciaraexecução
AocontráriodeumaJApplet,umaaplicaçãopodeconterummenu
## Appletse Aplicações

Interfaces Gráficos –Contentores e Gestores de Posicionamento
Marco Veloso –Programação Aplicada
## 33
–(1)Alterarocabeçalhodaclasse
herdadeJFrameemvezdeJApplet
–(2)Mudaronomedométodoinit()paraonomedaclasse,tornando-oassimno
construtordaclasse(eliminarvoiddocabeçalho)
–(3)Criarummétodomain(),inicializandooobjectocomainterface:
publicstaticvoidmain(String[]args){
JFramef=newNomeClasse();
f.setSize(300,300);
f.setVisible(true);
## }
–(4)AdicionarummétodohandleEventparatratarofimdaaplicação:
publicbooleanhandleEvent(Evente){
if(e.id==Event.WINDOW_DESTROY)
## System.exit(0);
returnsuper.handleEvent(e);
## }
–Casonãosejatratadooencerramentodaaplicação,ajanelaseráremovidado
ecrã,porémorespectivoprocessocontinuaráactivoeaconsumirrecursos
Converter appletsem aplicações

Interfaces Gráficos –Contentores e Gestores de Posicionamento
Marco Veloso –Programação Aplicada
## 34
OmétodohandleEventparatratarofimdaaplicaçãoestádefinidocomo
deprecated,nãosendoassimrecomendáveloseuusoFuturo.Em
alternativaexistem2possíveisprocessos:
–(4a)Associarumaacçãopordefeitoparaoencerramentodajanelaelibertação
dosrecursosreservados,casosepretendaapenasencerrarajanelanemacções
adicionais:
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
–(4b)CriarumaclasseTerminadorqueherdadainterfaceWindowAdaptere
reescreveométodowindowClosing(WindowEvente,sendooprocedimentoadequado
casosepretendarealizarváriasacçõesduranteoencerramento(e.g.encerraro
acessoaumSGBDoulibertarsockets)
classTerminadorJanelaextendsWindowAdapter{
publicvoidwindowClosing(WindowEvente){
## System.exit(0);
## }
## }
adicionandoassimumnovolisteneràaplicação
addWindowListener(newTerminadorJanela());
## Sepretenderimpediroutilizadordeencerrarajanelabastausaroparâmetro
DO_NOTHING_ON_CLOSE,tambémdisponívelnaclasseWindowConstants:
setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
Encerramento de Janelas (JFrame)

Interfaces Gráficos –Contentores e Gestores de Posicionamento
Marco Veloso –Programação Aplicada
## 35
## Aplicações

Interfaces Gráficos –Contentores e Gestores de Posicionamento
Marco Veloso –Programação Aplicada
## 36
(Container, BorderLayout)
(JPanel, GridLayout(2,1))
(JPanel, GridLayout(3,4))
(JPanel, FlowLayout)
(JPanel, FlowLayout)
## Aplicações

Interfaces Gráficos –Contentores e Gestores de Posicionamento
Marco Veloso –Programação Aplicada
## 37
“ProgramaçãoOrientadaaObjectos”
AntónioJoséMendes
DepartamentodeEngenhariaInformática,UniversidadedeCoimbra
“JavaSwing",2ªEdição
JamesElliott,RobertEckstein(Editor),MarcLoy,DavidWood,BrianCole
O'Reilly,ISBN:0596004087
"ThinkinginJava,",4ªEdição,Capítulo14“CreatingWindows&Applets”
BruceEckel
PrenticeHall,ISBN:0131872486
"TheJavaTutorial–CreatingaGUIwithJFC/Swing"
JavaSunMicrosystems
http://java.sun.com/docs/books/tutorial/uiswing/index.html
## Referências

Interfaces Gráficos –Contentores e Gestores de Posicionamento
Marco Veloso –Programação Aplicada
## 38
“TheDefinitiveGuidetoJavaSwing",3ªEdição
JohnZukowski
Apress,ISBN:1590594479
"FundamentosdeProgramaçãoemJava2",Capítulo12“InterfacesGráficos”
AntónioJoséMendes,MariaJoséMarcelino
## FCA,ISBN:9727224237
RevistoeadaptadoapartirdooriginaldeAntónioJoséMendeseMariaJoséMarcelino
## Referências