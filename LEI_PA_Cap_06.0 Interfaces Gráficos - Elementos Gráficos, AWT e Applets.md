

ProgramaçãoAplicada
## Interfaces Gráficos
Elementos Gráficos,  AWT e Applets
## Marco Veloso
marco.veloso@estgoh.ipc.pt
(Revistoe adaptadoa partirdo original de António José Mendes e Maria José Marcelino)

Interfaces Gráficos -Elementos Gráficos, AWT e Applets
Marco Veloso –Programação Aplicada
## 2
Packages,CodeConventions,FormataçõeseAdministração
Debugging&Logging
AcessoaBasesdeDadosrelacionaisatravésdeJDBC
SistemasConcorrentes(Threads)
ComunicaçãoemRede(Sockets)
InterfacesGráficos
## Agenda

Interfaces Gráficos -Elementos Gráficos, AWT e Applets
Marco Veloso –Programação Aplicada
## 3
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
## Sessão0
## Sessão1
## Sessão2
## Sessão3
## Sessão4
## Agenda

Interfaces Gráficos -Elementos Gráficos, AWT e Applets
Marco Veloso –Programação Aplicada
## 4
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

Interfaces Gráficos -Elementos Gráficos, AWT e Applets
Marco Veloso –Programação Aplicada
## 5
Elementos Gráficos, AWT e Applets
## Interfaces Gráficos

Interfaces Gráficos -Elementos Gráficos, AWT e Applets
Marco Veloso –Programação Aplicada
## 6
## Aplicação
## Podeserexecutadapelosistemaoperativonão
necessitandoquequalqueroutroprogramaainvoque
## (autónoma)
## Applet
## Nãopodeserexecutadaindependentemente(não
autónoma)
TemqueserchamadoapartirdeHTML(umapáginawebou
deum“Appletviewer”)
## Nestaprimeiraintroduçãoaosgráficosvamosusarapplets
Aplicações e Applets

Interfaces Gráficos -Elementos Gráficos, AWT e Applets
Marco Veloso –Programação Aplicada
## 7
## HTML
<TITLE>Applettitle</TITLE>
<APPLETCODE=Nome_do_Ficheiro.class
## WIDTH=“100”
## HEIGHT=“100”
## ALIGN=“MIDDLE”
## HSPACE=“0”>
## </APPLET>
Ambientesdeprogramação(IDEs)comooEclipseproduzem
automaticamenteocódigoHTMLnecessárioachamara
applet.
Integraaindaum“AppletViewer”quepermitevisualizara
appletsemrecorreraumbrowser
Visualização de Applets

Interfaces Gráficos -Elementos Gráficos, AWT e Applets
Marco Veloso –Programação Aplicada
## 8
JavadeixoudefornecersuporteparaAppletseAppletviewerapartir
daversão11doJSE
## Noentanto,asuaanáliseérelevanteparaoestudodemétodosgráficosno
desenhoeescritadeanimaçõesempainéis(JPanel)dabibliotecaAWT
(AbstractWindowToolkit),analisadosnasecçãoseguinte
AexecuçãodeumaappletnumJSEsuperioràversão11lançaráa
excepção:
java.lang.ClassNotFoundException:sun.applet.AppletViewer
## Seránecessáriousarumamáquinavirtualcomversãoanteriorà11(e.g.
JSE1.8)ouconverteraappletnumobjectoPanelouFrame
Suporte para Applets

Interfaces Gráficos -Elementos Gráficos, AWT e Applets
Marco Veloso –Programação Aplicada
## 9
## Ecrãgráfico
Osgráficosemcomputadorsãobaseadosempixels(Picture
## Element)
## Umpixeléumpontodoecrãquepodeserapresentadonuma
qualquercor(daspossíveisnocomputadoremcausa)
## Onúmerodepixelsdisponívelvariacomaresoluçãodaplaca
devídeodocomputadoredorespectivomonitor
## Resoluçõestípicasincluem640x480,800x600,1024x768,
## 1152x864,1280x1024e1980x1280
## Ecrã Gráfico

Interfaces Gráficos -Elementos Gráficos, AWT e Applets
Marco Veloso –Programação Aplicada
## 10
CoordenadasGráficas
## (0,0)
x
y
## Coordenadas Gráficas

Interfaces Gráficos -Elementos Gráficos, AWT e Applets
Marco Veloso –Programação Aplicada
## 11
## Umprimeirográfico:
importjava.awt.Graphics;
importjava.applet.Applet;
publicclassLinhaextendsApplet{
publicvoidpaint(Graphicsg){
g.drawRect(0,0,100,100);
## }
## }
UmaAppletnãotemmétodomain(),massimummétodopaint()
responsávelpordesenharouactualizaroselementosgráficosnoecrãapós
umaalteração
OmétodopaintdevolveoobjectoGraphicsquerepresentaatelasobre
aqualserãodesenhadososelementosgráficos(e.g.g.drawRect),sendo
igualmenteinvocadosemprequeoecrãnecessitadeseractualizado
Importar bibliotecas
Classe é baseada em Applet
Desenha um rectângulo
iniciando no ponto (0,0) com
100 pixeisde comprimento e
100 pixeisde altura
grepresenta a área de
desenho
painté chamado
automaticamente
Exemplo de um gráfico em Applet

Interfaces Gráficos -Elementos Gráficos, AWT e Applets
Marco Veloso –Programação Aplicada
## 12
Umavezqueocódigoanterior(Applet)nãoéexecutávelcomJSE11ou
superior,poderemosadaptarocódigoparaquerecorraaumpainel
(Panel)aoinvésdeumaapplet:
importjava.awt.Graphics;
importjava.awt.Panel;
publicclassLinhaextendsPanel{
publicvoidpaint(Graphicsg){
g.drawRect(10,10,100,100);
## }
## }
## Estavoltaaserumaaplicaçãoautónoma,necessitandoassimdomain()
parainiciar.ÉcriadaumaFrame(janelaautónoma)ondeéadicionadoum
painel(Panel),quepermiteobteroobjectoGraphicsparadesenhar
importjavax.swing.JFrame;
publicstaticvoidmain(String[]args){
JFrameapp=newJFrame();
app.add(newLinha());
app.setSize(400,400);
app.setVisible(true);
## }
Exemplo de um gráfico em Panel

Interfaces Gráficos -Elementos Gráficos, AWT e Applets
Marco Veloso –Programação Aplicada
## 13
Exemplo de um gráfico em AppletePanel

Interfaces Gráficos -Elementos Gráficos, AWT e Applets
Marco Veloso –Programação Aplicada
## 14
ElementosGráficos
ElementosGráficos

Interfaces Gráficos -Elementos Gráficos, AWT e Applets
Marco Veloso –Programação Aplicada
## 15
## Métodos Gráficos
MétodoFuncionalidade
publicabstractvoiddrawArc
## (intx,inty,intwidth,intheight,int
startAngle,intarcAngle)
desenhaumsegmentodearco.Osargumentosxeyespecificamo
cantosuperioresquerdodorectânguloenvolventeewidtheheight
oseucomprimentoelargura.OsargumentosstartAnglee
arcAngleespecificamosângulosdeinícioedetamanhodoarco,
respectivamente.
publicabstractvoidfillArc
## (intx,inty,intwidth,intheight,int
startAngle,intarcAngle)
## Idem,paraumarcoacheio(interiorpreenchido).
publicabstractvoiddrawOval
## (intx,inty,intwidth,intheight)
desenhaumaovalinscritanorectângulodecantosuperioresquerdo
nopontox,yecomprimentowidthelarguraheight.
publicabstractvoidfillOval
## (intx,inty,intwidth,intheight)
## Idemparaumaovalacheio.
publicabstractvoiddrawLine
## (intx1,inty1,intx2,inty2)
desenhaumsegmentoderectaentreospontos(x1,y1)e(x2,y2).
publicvoiddrawRect
## (intx,inty,intwidth,intheight)
desenhaumrectângulocomcantosuperioresquerdonoponto(x,
y)ecomprimentowidthelarguraheight.
publicabstractvoidfillRect
## (intx,inty,intwidth,intheight)
## Idemparaumrectânguloacheio.
publicabstractvoidclearRect
## (intx,inty,intwidth,intheight)
## Apagaorectângulodesenhado
publicabstractvoiddrawString
(Stringstr,intx,inty)
## Escreveastringstrnaposição(x,y).
publicvoidsetBackground(Colorc)
## Colocaacordefundoac.
publicabstractvoidsetColor(Colorc)
Defineacordoselementosadesenhar.Apósachamadadeste
métodotodososelementosserãodesenhadoscomacorc.
publicabstractvoidsetColor
## (intr,intg,intb)
Omesmoqueométodoanterior,noentantodefinidoosvaloresRGB
## (0-255).

Interfaces Gráficos -Elementos Gráficos, AWT e Applets
Marco Veloso –Programação Aplicada
## 16
OsmétodosgráficossãoaplicadossobreoobjectoGraphics
quepodeserobtidoatravésdométodopaint(Graphicsg),tanto
emappletscomoempainéis(Panel):
g.drawRect(0,0,5,5);
OobjectoGraphicsrepresentaatela(ecrã)sobreaqualserão
desenhadososvárioselementosgráficos(e.g.linhas,
rectângulos,ovais,texto)
## Ométodopaint()éinvocadosemprequeoecrãforalteradoeé
necessárioredesenharatela
TambémépossívelobteroobjectoGraphicssolicitando
directamentesobreoelementográficoqueestamosamanipular
atravésdométodogetGraphics():
Panelpainel=newPanel();
Graphicsg=painel.getGraphics();
## Métodos Gráficos

Interfaces Gráficos -Elementos Gráficos, AWT e Applets
Marco Veloso –Programação Aplicada
## 17
drawRect
g.drawRect(10,20,90,50);
fillRect(x,y,width,height);
drawRoundRect(x,y,w,h,arcWidth,arcHeight);
fillRoundRect(x,y,w,h,arcWidth,arcHeight);
clearRect();
Ângulo do canto(10,10)      (10,10)       (25,50)       (50,25)
## Métodos Gráficos

Interfaces Gráficos -Elementos Gráficos, AWT e Applets
Marco Veloso –Programação Aplicada
## 18
draw3DRect
g.draw3DRect(10,20,90,50,true);
fill3DRect(x,y,width,height,salient);
clear3DRect();
g.draw3DRect(25,10,50,75,true);
g.draw3DRect(25,110,50,75,false);
g.fill3DRect(100,10,50,75,true);
g.fill3DRect(100,110,50,75,false);
Nota:ousode3DapenasévisívelquandoaappletéintegradaemPanels
## Métodos Gráficos

Interfaces Gráficos -Elementos Gráficos, AWT e Applets
Marco Veloso –Programação Aplicada
## 19
drawOval
g.drawOval(10,20,90,50);
fillOval(x,y,width,height);
clearOval();
drawArc
g.drawArc(10,20,90,50,0,90);
fillArc(x,y,width,height,
start_angle,end_angle);
clearArc();
## Métodos Gráficos

Interfaces Gráficos -Elementos Gráficos, AWT e Applets
Marco Veloso –Programação Aplicada
## 20
drawPolygon
drawPolygon(x,y,length);
clearPolygon();
## Ex.int[]x={10,23,24,56,10};
int[]y={10,30,40,20,10};
g.drawPolygon(x,y,x.length);
## Métodos Gráficos
## (0,0)

Interfaces Gráficos -Elementos Gráficos, AWT e Applets
Marco Veloso –Programação Aplicada
## 21
## Obterasdimensõesdeumacomponentegráfica
(Panel,Applet):
intmaxX=getSize().width;
intmaxY=getSize().height;
## Max Width
## Max Height
## (0,0)
(maxX, maxY)
## .
## .
Dimensões de um janela gráfica

Interfaces Gráficos -Elementos Gráficos, AWT e Applets
Marco Veloso –Programação Aplicada
## 22
Definiçãodecores(setBackground,setColor)
## Definiracorapartirdeumapaletedecorespré-definida:
black,blue,cyan,darkGray,gray,green,lightGray,
magenta,orange,pink,red,white,yellow
setColor(Color.red);//Há13corespré-definidas
DefiniracorrecorrendoaométodoRGB:
Colorc=newColor(R,G,B);//Red,Green,Blue
Colorc1=newColor(0,255,0);//verde
Colorc2=newColor(117,23,48);//castanho
setColor(c1);//usandoumobjectodotipoColor
setColor(newColor(255,0,0));//recorrendoaométodoRGB
OmétodogetBackground()devolveacordefundodoobjecto
Definição de Cores

Interfaces Gráficos -Elementos Gráficos, AWT e Applets
Marco Veloso –Programação Aplicada
## 23
## Maisalgunsmétodosgráficos
drawString
drawString(String,x,y);
## Permiteescrevercadeiasdecaracteresnaáreagráfica
Ex.g.drawString(“Bomdia!”,10,20);
Escreveafrase“Bomdia!”apartirdoponto(10,20)
## Usaafonteeoestiloqueestiveremactivos
Escrita em janelas gráficas

Interfaces Gráficos -Elementos Gráficos, AWT e Applets
Marco Veloso –Programação Aplicada
## 24
Épossívelmodificarosparâmetrosdafonte(classeFont)paraobterum
textoformatadopretendido.
ParaoefeitoénecessárioinvocarumobjectodeumaclasseFont,
definidonapackagejava.awt,definindotrêsargumento:nomedafonte,
estilodafonteetamanhodafonte,comaseguinteasuasintaxe:
Fontf=newFont(“Nome_da_Fonte”,
Estilo_da_Fonte,
## Tamanho
## );
AseguiràdeclaraçãodoobjectodaclasseFonténecessárioutilizarum
métododenominadosetFont(),paraestaseraplicadaaoobjectoda
classeGraphicsqueestiveraserutilizado
g.setFont(f);
Definição de fontes

Interfaces Gráficos -Elementos Gráficos, AWT e Applets
Marco Veloso –Programação Aplicada
## 25
O“Nome_da_Font”éumaStringcontendoonomepeloqualafonteé
conhecida.Algumasdasfontesdisponíveissãoasseguintes:
‒“TimesRoman”,
‒“Courier”,
‒“Terminal”,
‒“Helvetica”,
‒“Dialog”e
‒“Symbol”(Symbol)
O“Estilo_da_Font”éumavariávelquerepresentaostiposde
formataçãodetextoadicionaisquegostamosdedaraosnossostextos,
porexemplo,realçandoaspalavrasanegrito(Bold)ouaItálico.Alista
completaestádefinidanaclasseFont,masosestilosmaisusuaissãoo
‒“Font.PLAIN”,
‒“Font.BOLD”,
‒“Font.ITALIC”
O“Tamanho”indicaonúmerodepontosquedevemrepresentaraletra
Definição de fontes

Interfaces Gráficos -Elementos Gráficos, AWT e Applets
Marco Veloso –Programação Aplicada
## 26
importjava.awt.Graphics;
importjava.awt.Font;
Importjava.applet.Applet;
publicclassTesteextendsApplet{
publicvoidpaint(Graphicsg){
Fontf1=newFont(“TimesRoman”,Font.PLAIN,12);
Fontf2=newFont(“Courier”,Font.BOLD,24);
g.setFont(f1);
g.drawString(“Textonormalde12Pontos”,10,20);
g.setFont(f2);
g.drawString(“TextoCourieremnegritode24Pontos”,
## 10,50);
## }
## }
Exemplo de definição de fontes

Interfaces Gráficos -Elementos Gráficos, AWT e Applets
Marco Veloso –Programação Aplicada
## 27
Exemplo de definição de fontes

Interfaces Gráficos -Elementos Gráficos, AWT e Applets
Marco Veloso –Programação Aplicada
## 28
importjava.awt.*;
importjava.awt.print.*;
publicclassHelloWorldPrinter
implementsPrintable{//Step1
privateStringtextoToPrint="HelloWorld!";
publicintprint(Graphicsg,
PageFormatpf,
intpage)
throwsPrinterException{
Graphics2Dg2d=(Graphics2D)g;
//conversãodoreferencial(X,Y)parao
## //formatodafolhaaimprimir,definindo
## //aposiçãodoponto(0,0)
g2d.translate(pf.getImageableX(),
pf.getImageableY());
//Definiçãodoconteúdoaimprimir
g.drawString(textToPrint,100,100);
if(page>0){
## //sópossuimosumapágina
returnNO_SUCH_PAGE;
## }else
returnPAGE_EXISTS;
## }
## }
publicvoidprintPage(){
//Step2
PrinterJobjob=PrinterJob.getPrinterJob();
//Step3
job.setPrintable(this);
//job.setPrintable(newHelloWorldPrinter());
//Step4
booleanok=job.printDialog();
if(ok){
try{
//Step5
job.print();
}catch(PrinterExceptionex){
//Falhanaimpressão
## }
## }
## }
publicstaticvoidmain(Stringargs[]){
HelloWorldPrinterhello=
newHelloWorldPrinter();
hello.printPage();
## }
## }//endofclass
## Impressão

Interfaces Gráficos -Elementos Gráficos, AWT e Applets
Marco Veloso –Programação Aplicada
## 29
## Sendoumaappletintegradanoutra
aplicação(appletviewer),oseutamanhoé
definidopelorespectivoappletviewer
Porexemplo,noIDEEclipse,épossível
definirotamanhodecadaappletatravés
dasconfiguraçõesdeexecução:
run>runconfigurations>parameters
alterandoosvaloresdoscamposWidthe
## Height,quedefinemocomprimentoelargura
inicialdaapplet.Esteprocedimentopassaos
valorescomoparâmetrosparaaapplet
duranteaexecução
## Porém,épossívelforçarotamanhoinicial
daappletporcódigo,atravésdainvocação
dométodosetSize(comp,larg).Este
métododeveserinvocadoatravésdo
métododearranquedasapplets(init())
Dimensões de uma Applet

Interfaces Gráficos -Elementos Gráficos, AWT e Applets
Marco Veloso –Programação Aplicada
## 30
Exemplo de animações gráficas

Interfaces Gráficos -Elementos Gráficos, AWT e Applets
Marco Veloso –Programação Aplicada
## 31
Fasesde umaApplet
Fasesde umaApplet

Interfaces Gráficos -Elementos Gráficos, AWT e Applets
Marco Veloso –Programação Aplicada
## 32
Nãoexistindoummétodomain(),existemnaAppletumconjuntode
métodosquerepresentamasfasesmaisimportantesdavidadeuma
## Applet:
Inicialização–EstaéafaseemqueaAppletépelaprimeiravezcarregada.
## Nestafasepodemserrealizadasacções,taiscomo,acriaçãodeobjectos,
inicializaçãodevariáveis,etc.,invocadaantesdométodopaint().O
métodocorrespondenteaesteestadoéoinit().UmaAppletsópassapor
estafaseumavez
Começar–Estafaseocorreaseguiràinicialização,ouentão,depoisdea
Applettersofridoumaparagem.Aocontráriodafaseanterior,umaApplet
podepassarváriasvezespelafase“Começar”.Ométodocorrespondenteé
ostart()
Parar–EstafaseimplicaumaparagemnaexecuçãodaApplet.Émuitas
vezesutilizadaemconjuntocomoestado“Começar”,paracertostipode
comportamentodeApplets.Ométodocorrespondenteéostop()
Destruir–EsteéoestadoterminaldeumaApplet.Ométodoassociadoéo
destroy()
Fases de uma Applet

Interfaces Gráficos -Elementos Gráficos, AWT e Applets
Marco Veloso –Programação Aplicada
## 33
Fases de uma Applet

Interfaces Gráficos -Elementos Gráficos, AWT e Applets
Marco Veloso –Programação Aplicada
## 34
EstesquatrométodosestãodefinidosnaclasseApplet,entremuitosoutros
Contudo,sefordefinidaumaAppletvazia,oseucomportamentotambémserá
nulo,umavezquenenhumdosmétodosexecutaalgumaacção
## Paracriarcomportamentosénecessárioreescreverosmétodos
(Override).Paraissobastadeclararosmétodosnointeriordocódigodas
## Applets,tendoocuidadodeosdeclararcomopublic
## Porexemplosepretendermosdefinirumcomportamentoinicialparauma
determinadaapplet,serianecessáriofazeradeclaraçãodométodoinit():
publicvoidinit(){
## //códigodedefiniçãodocomportamento
setSize(300,100);
## }
## Ocomportamentodeumaappleté,normalmente,definidoàcustada
reescritadosmétodosinit(),start()estop()
## Nãoéhabitualassociarcomportamentosaométododestroy()
Fases de uma Applet

Interfaces Gráficos -Elementos Gráficos, AWT e Applets
Marco Veloso –Programação Aplicada
## 35
OcódigodestesmétodossóseráexecutadoquandoaAppletestiver
noestadocorrespondente
## Emtodososcasosemquetenhamosdesenhosouoperações
gráficassobreaApplet,iremosrecorreraummétododesignado
porpaint(),invocadosemprequesepretendaenviaralgoparao
ecrã
## Esteseráutilizadováriasvezesduranteaexecuçãodeuma
applet,porexemplo,paragerarumaanimação(repetidosenviosde
imagensparaumajanela)oumesmopararegenerarumaimagem
quefoitapadaporumajanela
## Oredesenhodaappletpodeserinvocadodeliberadamente
atravésdométodorepaint(),oqueforneceasbasesparauma
animaçãosimples
Fases de uma Applet

Interfaces Gráficos -Elementos Gráficos, AWT e Applets
Marco Veloso –Programação Aplicada
## 36
ÉpossívelpassarparâmetrosparaointeriordeumaApplets.Estes
parâmetrossãoidentificadosporumnomeeporumvalor.Asintaxea
utilizaréaseguinte:
<APPLETCODE="%s"
NAME="%s"WIDTH=400HEIGHT=300ALIGN=middle>
## <paramvar1value=“valor1”>
## <paramvar2value=“valor2”>
## (...)
<paramvarNvalue=“valor3N”>
## </APPLET>
OvalorpassadopodeserrecuperadopeloprogramadadaApplet,no
interiordométodoinit(),sendoparatalchamadoummétodo
denominadogetParameter(),queaceitacomoargumentoonomeda
variávelapassar(sempreumaString)
## Nocasodeserpretendidopassarumvalordeoutrotipoquenãouma
String,aconversãotemqueserfeitadepoisdeobtidaaStringcomo
métodogetParameter()
Passagem de parâmetros às Applets

Interfaces Gráficos -Elementos Gráficos, AWT e Applets
Marco Veloso –Programação Aplicada
## 37
## Esteexemploserveparailustrarapassagemdeparâmetrosparauma
AppletJava
importjava.awt.Graphics
importjava.applet.Applet;
publicclassTesteextendsApplet{
## Stringfrase;
publicvoidinit(){
frase=getParameter(“var1”);
## }
publicvoidpaint(Graphicsg){
g.drawString(frase,10,25);
## }
## }
AStringaenviarparaoecrãépassadaporintermédiadavariável“frase”,
quetomaovalordoparâmetrodenome“var1”porexecuçãodométodo
getParameter()
Passagem de parâmetros às Applets

Interfaces Gráficos -Elementos Gráficos, AWT e Applets
Marco Veloso –Programação Aplicada
## 38
OcódigoHTMLcorrespondente,ondesefazapassagemdoreferidoparâmetro,é
oseguinte:
## <HTML>
## <HEAD>
<TITLE>AppletTestPage</TITLE>
## </HEAD>
## <BODY>
<APPLETCODE="Teste.class"
WIDTH=400HEIGHT=300ALIGN=middle>
## <PARAM
NAME=“var1”
VALUE=“Aminhaappletmodificada!”>
## </APPLET>
## </BODY>
## </HTML>
OtextodesenhadopelaApplettem,nestecaso,aparticularidadeespecial
detersidopassadoparaoseuinterior,atravésdocódigoHTML.
## Paraoutilizadorfinaloefeitoéomesmo,masautilizaçãodeparâmetros
passadosatravésdeHTMLvemtrazermaisflexibilidade,aopermitiro
ajustedosparâmetrosdirectamentepeloutilizador,semnecessidadede
conhecimentodaAppletesemcompilação.
Passagem de parâmetros às Applets

Interfaces Gráficos -Elementos Gráficos, AWT e Applets
Marco Veloso –Programação Aplicada
## 39
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

Interfaces Gráficos -Elementos Gráficos, AWT e Applets
Marco Veloso –Programação Aplicada
## 40
“TheDefinitiveGuidetoJavaSwing",3ªEdição
JohnZukowski
Apress,ISBN:1590594479
"FundamentosdeProgramaçãoemJava2",Capítulo12“InterfacesGráficos”
AntónioJoséMendes,MariaJoséMarcelino
## FCA,ISBN:9727224237
RevistoeadaptadoapartirdooriginaldeAntónioJoséMendeseMariaJoséMarcelino
## Referências