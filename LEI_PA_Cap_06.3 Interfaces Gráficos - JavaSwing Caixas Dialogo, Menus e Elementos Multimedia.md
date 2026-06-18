

ProgramaçãoAplicada
## Interfaces Gráficos
Caixas Dialogo, Menus e Elementos Multimédia
## Marco Veloso
marco.veloso@estgoh.ipc.pt

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
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

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 3
Interacção com o utilizador
Caixas de diálogo e menus
Interacção com o utilizador

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 4
Caixas de diálogo
Caixas de diálogo

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 5
Caixas de diálogo

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 6
OutroscomponentesdeumainterfacegráficaqueoSwingtempredefinidos
sãoascaixasdediálogo
## Umadestascaixasdediálogoparticularmenteútilparaparaselecçãode
ficheiros(leituraouescrita)édefinidapelaclasseJFileChooser
JFileChooserdLerFich= newJFileChooser();
dLerFich.showDialog(this,“Open”);//mostracaixadediálogo
File file= dLerFich.getSelectedFile(); //obtém nome do ficheiro
## Alémdaconstruçãodoobjecto,usandoooperadornewcomoéusual,é
necessárioinvocarométodoshowDialog()paraacaixaficarvisível
## Osparâmetrosparaestemétodosão:oobjecto-paidacaixa(deveser
umajanela(JFrame)),eotítulo(umaString).
OmétodogetSelectedFile()éusadoparaobtermosumareferência
## (handle)paraoficheiroqueseleccionámos
Caixas de diálogo

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 7
## Podemosespecificarqualadirectoriaacaixadediálogodeve
abrir,indicandoessainformaçãocomoargumentodoconstrutor
JFileChooser(Stringdir);
porexemplo“.”indicaadirectoriacorrente,
ouatravésdapropriedadesetCurrentDirectory
setCurrentDirectory(newjava.io.File(".")).
## Paraconheceradirectoriadefinidarecorre-seaométodo
getCurrentDirectory()
Caixas de diálogo

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 8
## Paraaaberturadedirectorias(quepermiteacederatodososficheiros
incluídosnessapasta)énecessáriodefinir,naclasseJFileChoosera
propriedadesetFileSelectionMode.
setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY)
## Apropriedadeseleccionadanoexemploapenaspermiteacedera
directorias,omitindoosficheiros.
ÉpossívelinvocarométodogetFileSelectionModeparaconhecero
mododeselecçãodefinido.
## Existemtrêsmodospossíveis:
FILES_AND_DIRECTORIES(opçãopordefeito),
FILES_ONLYe
## DIRECTORIES_ONLY
Caixas de diálogo

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 9
Qualquerquesejaomododeselecção,ométodogetSelectedFileirá
devolverumobjectoFile
CasosejaactivadoomodoDIRECTORIES_ONLY,oobjectoFilerecebido
éumareferênciaparaumadirectoria,nãoumficheiro
Atravésdométodolist(),daclasseFile,épossívelobterumaestrutura
(arraydeStrings)comosnomesdosficheirosdoobjectoFile
recebido
Nestecenárioéaconselhávelremoveraopção“AllFiles”dajanelade
diálogo,atravésde
setAcceptAllFileFilterUsed(false).
Caixas de diálogo

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 10
ApropriedadesetMultiSelectionEnabledpermitedefiniromodode
selecçãodeficheirosoudirectoriasdaCaixadediálogo
## Ovalortruepermiteseleccionarmaisdoqueumelemento,ovalor
false(valorpordefeito)defineapenasumelementoseleccionado
quandoacaixadediálogoéinvocada
## Casoovalorfalsesejaescolhido,acaixadediálogodevolveum
objectodotipoFile,atravésdométodo
getSelectedFile()
## Casoovalortruesejaseleccionado,acaixadediálogodevolveumarray
deobjectosFile(File[]),sendonecessárioinvocarométodo
getSelectedFiles()
Caixas de diálogo

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 11
JFileChooserdLerFich=newJFileChooser(”.“);
//Alternativa:dLerFich.setCurrentDirectory(newjava.io.File("."));
dLerFich.setDialogTitle(“Seleccionarficheiro”);
dLerFich.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//Desactivaaopção"Allfiletypes":
dLerFich.setAcceptAllFileFilterUsed(false);
if(dLerFich.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
System.out.println("CurrentDir.:"+dLerFich.getCurrentDirectory());
System.out.println("SelectedFile:"+dLerFich.getSelectedFile());
else{
System.out.println("NoSelection");
## }
//Alternativasimplificada:
dLerFich.showDialog(this,"Seleccionarficheiro");
Filefile=dLerFich.getSelectedFile();
StringnomeDirectoria=file.getAbsolutePath();
Caixas de diálogo

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 12
Caixas de Mensagem
Caixas de diálogo

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 13
## Outrotipodecaixasdediálogopermitemdisponibilizarmensagensgenéricasao
utilizadoreaguardarasuaacção,definidaspelaclasseJOptionPane
## Destacam-sedoistiposdecaixasdedemensagem
CaixasdeMensagem(MessageDialog)–apresentamumamensagemao
utilizador,massemacção/escolhadoutilizador
CaixasdeConfirmação(ConfirmDialog)–apresentamumamensagem
aoutilizador,aguardandoumaacção/escolhadoutilizador
Caixas de mensagem

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 14
## Ascaixasdemensagempermitemapresentarmensagensaoutilizador
(showMessageDialogdaclasseJOptionPane),emboranãopermitama
interacçãocomoutilizador:
JOptionPane.showMessageDialog(this,
"Mensagem a Transmitir",
"Título da Mensagem",
JOptionPane.ERROR_MESSAGE);
## Eoresultadoéoseguinte,sendopossíveldefinirotipodemensagemaapresentar
## (informação,aviso,erro,etc.),alterandoovalordoúltimoparâmetro:
Entreoutras,podemosrecorreraostipos(definidosnaclasseJOptionPane):
OK_OPTION(pordefeito),OK_CANCEL_OPTION,CANCEL_OPTION,
## ABORT_OPTION,ERROR_OPTION,QUESTION_MESSAGE,
## WARNING_MESSAGE,PLAIN_MESSAGE,INFORMATION_MESSAGE
noentantodisponibilizasempreomesmobotãoOK,apenasmodificandoo
sinaldeaviso
Caixas de mensagem

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 15
## Parapermitirqueoutilizadordefinaorumoaseguir(seaceitaounãouma
informação,porexemplo)necessitamosdeoutrotipodecaixasdediálogo
(showConfirmDialogdaclasseJOptionPane):
intvalor = JOptionPane.showConfirmDialog(this,
"Mensagem a Transmitir",
"Título da Mensagem",
JOptionPane.OK_CANCEL_OPTION);
## Eoresultadoéoseguinte:
ConsoanteoutilizadorpressioneemOKouCANCEL,avariávelvalor
armazenará0ou2
Caixas de mensagem interactivas

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 16
## Épossívelalteraralinguagemdeapresentaçãodosbotões.
## Umaabordagemserádefiniralinguagempordefeitonoarranqueda
aplicação,definindoapropriedadeuser.language:
java-Duser.language=PTClassName
## Outraabordageméindicarnajanelagráficaotextoassociadoacadabotão:
UIManager.put("OptionPane.cancelButtonText", "Cancelar");
UIManager.put("OptionPane.noButtonText", "Não");
UIManager.put("OptionPane.okButtonText", "Confirmar");
UIManager.put("OptionPane.yesButtonText", "Sim");
Caixas de mensagem interactivas

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 17
## Paraalémdemensagens,ascaixasdediálogo(oucaixasdemensagem)
tambémpodemincluiroutrascomponentes.
ParaoefeitodeveserdisponibilizadoumarraydeJComponentcomos
elementosaseremvisualizados:
JLabelmyLabel1=newJLabel(“Field1:”),
JTextFieldmyText1=newJTextField(10),
JLabelmyLabel2=newJLabel(“Field2:”),
JTextFieldmyText2=newJTextField(10)
JComponent[]inputs=
newJComponent[]{myLabel1,
myText1,
myLabel2,
myText2};
intvalor=JOptionPane.showConfirmDialog(null,
inputs,
"Message",
JOptionPane.OK_CANCEL_OPTION);
Stringtext=myText1.getText();
Caixas de mensagem interactivas

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 18
## Existemoutrostiposdecaixasdediálogo,comdiferentesfinseutilizações
(e.g.showInputDialog;showOptionDialog)
OsprópriosmétodosshowConfirmDialog()eshowMessageDialog()
apresentamdiferentesassinaturas,comdiferentenúmeroetipode
parâmetrosdeentrada
AsclassesJOptionPaneeJDialogsãoresponsáveispelamanipulação
dosdiversostiposdecaixasdediálogodisponibilizadaspeloJavaSwing
Caixas de mensagem

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 19
## Ascaixasdediálogosãoigualmenteúteisparacontrolaraexecuçãoda
aplicação,especialmenteemciclosqueexigemaberturadenovasjanelas
numciclo
## Casosejanecessárioiterarsobrevárioselementos,eparacadaelemento
apresentarumanovajanela(e.g.formulário),devendoaaplicaçãoprincipal
aguardaremcadaiteraçãoqueoutilizadortermineanovajanela,anova
janeladeveherdardaclasseJDialogenãodeJFramesendo
disponibilizadaumareferênciadajanelaprincipalqueinvocaacaixadediálogo
NaclassequeherdadeJDialog,quandosepretendertransferiraexecução
paraaaplicaçãoprincipal(e.g.outilizadorpressionouobotão“OK”),deve-se
invocarainstruçãodispose()
## Destaforma,emcadaiteraçãodociclo,aexecuçãoétransferidaparaanova
janela(JDialog)eapenasquandoaJDialoginvocarainstruçãodispose()
équeaexecuçãoétransferidanovamenteparaaaplicaçãoprincipal
Caixas de mensagem

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 20
//Janeladediálogo,herdadeJDialogeoconstrutorinvocaosuperconstrutor
publicclassMyLoginextendsJDialogimplementsActionListener{
//...Code
MyLogin(JFrameparent){//recebeumareferência(parent)daappprincipal
super(parent,"Login",true);//invocaosupercontrutor
//...Code
## }
publicvoidactionPerformed(ActionEvente){
if(e.getSource().equals(ok)){
//...Code
dispose();//notificaaclasseprincipal,transferindoocontrolodeexecução
## }
## }
## }
## //classeprincipal,emcadaiteraçãoocicloinvocaumanovajaneladediálogoeaguarda
## While(condition){
//...Code
MyLoginlogin=newMyLogin(this);
login.setVisible(true);
//...aguardaatéqueainstruçãodispose()sejainvocadanaJDialog
//...Code
## }
Caixas de mensagem

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 21
## Menus
## Menus

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 22
## Paracriarmenusteremosquerecorreràclasse
JMenuBar
CadaelementodomenuécriadoatravésdaclasseJMenu,enquantocada
secçãodeummenuéobtidaatravésdaclasseJMenuItem
ParaadicionaromenuàbarradeferramentasdaJFramerecorremosao
método
setJMenuBar()
## Aassociaçãodeeventosérealizadadaformatradicional,ouseja,
recorrendoàclasseActionListener,indicandoqualoJMenuItema
serobservadopelogestordeeventosatravésdométodo
addActionListener()
## Otratamentodoevento(quandoalguémseleccionaoelementodomenu)
continuaaserdaresponsabilidadedométodo
actionPerformed(ActionEvente)
## Menus

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 23
JMenuBar
JMenu
JMenuItem
## Menus

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 24
JMenuBarmainBar= new JMenuBar( ); // criamenu principal
// FILE Menu
JMenufileMenu= new JMenu( "File" );
fileMenu.setMnemonic( KeyEvent.VK_F); // Alt+F
// File+About
JMenuItemaboutItem= new JMenuItem( "About..." );
aboutItem.setMnemonic( KeyEvent.VK_A); // ALT+A
aboutItem.addActionListener(this);
// File+Exit
JMenuItemexitItem= new JMenuItem( "Exit" );
exitItem.setMnemonic( KeyEvent.VK_X);
exitItem.addActionListener(this);
fileMenu.add( aboutItem);
fileMenu.add(exitItem);
mainBar.add( fileMenu);
setJMenuBar( mainBar); // adicionamenu à aplicação
## Menus

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 25
E o respectivométodoactionPerformed()paragestãodos eventos
sobreo menu:
public void actionPerformed( ActionEvente ){
if ( e.getSource().equals(aboutItem) )
JOptionPane.showMessageDialog(
## Menu2.this,
"Isto é um exemplo\n de utilização de menus",
"About",
JOptionPane.PLAIN_MESSAGE
## );
if (e.getSource().equals(exitItem))
## System.exit( 0 );
repaint();
## }
## Menus

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 26
JMenuBar
JMenu
Menus e submenus

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 27
JMenuBarmainBar=newJMenuBar();
## (...)
//FORMATMenu
JMenuformatMenu=newJMenu("Format");
//Format+Color
JMenucolorMenu=newJMenu("Color");
String[]colors={"Black","Blue","Red","Green"};
JRadioButtonMenuItem[]colorItems=newJRadioButtonMenuItem[colors.length];
ButtonGroupcolorGroup=newButtonGroup();
for(inti=0;i<colorItems.length;i++){
colorItems[i]=newJRadioButtonMenuItem(colors[i]);
colorMenu.add(colorItems[i]);//adicionaosRadioButton
colorGroup.add(colorItems[i]);//agrupa-os
colorItems[i].addActionListener(this);//adicionaeventoacadabotão
## }
colorItems[0].setSelected(true);//opçãopordefeito
formatMenu.add(colorMenu);
Menus e submenus

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 28
//Font(Format+Syle)
JMenufontMenu=newJMenu("Font");
String[]fontName={"TimesRoman","Courier","Helvetica"};
JRadioButtonMenuItem[]fontItem=newJRadioButtonMenuItem[fontName.length];
ButtonGroupfontGroup=newButtonGroup();
for(inti=0;i<fontItem.length;i++){
fontItem[i]=newJRadioButtonMenuItem(fontName[i]);
fontMenu.add(fontItem[i]);
fontGroup.add(fontItem[i]);
fontItem[i].addActionListener(this);
## }
fontItem[2].setSelected(true);
fontMenu.addSeparator();
Menus e submenus

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 29
String[]styleNames={"Bold","Italic"};
JCheckBoxMenuItem[]styleItems=newJCheckBoxMenuItem[styleNames.length];
for(inti=0;i<styleItems.length;i++){
styleItems[i]=newJCheckBoxMenuItem(styleNames[i]);
fontMenu.add(styleItems[i]);
styleItems[i].addActionListener(this);
## }
formatMenu.add(fontMenu);
//Menuprincipal
mainBar.add(formatMenu);
Menus e submenus

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 30
## Elementos Multimédia
(Imagens, Ícones, Sons)
em Frames
## Elementos Multimédia

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 31
Elementos Multimédia em Frames: Imagens
## Elementos Multimédia

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 32
## Aintegraçãodeimagensemframesrecorreadiferentesabordagense
componentes:
-JLabel(integra-seaimagematravésdeumImageIcon);
-JButton(integra-seaimagematravésdeumImageIcon);
-JPanel(necessárioreescreverométodopaint);
Integração de imagens (frames)

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 33
ParaascomponentesJLabeleJButton,noessencial,oprocessorequera
criaçãodeumobjectoImage(quecarregaaimagemapartirdosistema
deficheiros),sendoadicionadoàsváriascomponentesatravésdaclasse
ImageIcon:
Imageimage=Toolkit.getDefaultToolkit().getImage(“images/pic.jpg”);
JLabelmyLabel=newJlabel(newImageIcon(image));
JButtonmyButton=newJButton(newImageIcon(image));
## Alternativamente,podemosadicionaraimagemapósacriaçãodoobjecto:
JLabelmyLabel=newJLabel(“LoremIpsum”);
myLabel.setIcon(newImageIcon(image));
## Paraumacessouniversalaoficheirodeimagem:
Imageimage=Toolkit.getDefaultToolkit().getImage(
getClass().getResource(“images/pic.jpg”)
## );
Integração de imagens (frames–JLabel, JButton)

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 34
Épossívelmanipularostradicionaisformatosdeimagensgráficas(e.g.JPG,GIF,
## PNG,BMP)
Adicionalmente,épossívelredimensionaraimagemaintegrarnainterface.Parao
efeitocria-seumanovaimagemapartirdoredimensionalmentedaimagemoriginal:
Imageimage=Toolkit.getDefaultToolkit().getImage(“image1.jpg");
ImagescaledImage=
image.getScaledInstance(100,100,Image.SCALE_DEFAULT);
## Nesteexemploestáaforçar-seaimagemaserredimensionadaparaumadimensão
de100x100
Esteredimensionamentoemimagensbitmap(e.g.JPG,GIF,PNG)podeimplicara
percadequalidade.Existemváriosalgoritmosparalidarcomessaperca.Omais
comuméatravésdapropriedadeSCALE_DEFAULT
–OalgoritmoSCALE_FASTdáprioridadeàvelocidade;
–OalgoritmoSCALE_SMOOTHfocaaqualidadedaimagememdetrimentoda
velocidade;
–OalgoritmoSCALE_AREA_AVERAGINGusaumamédiadasáreasparagarantir
queaimagemfinalmantemidênticasproporçõesàimagem,origina
Redimensionamento de imagens

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 35
NocasodeJPanelainclusãodeimagenspassapelareescritadométodo
paintComponentoureescritadométodopaint,umavezqueestes
métodos,possuindoumobjectoGraphics,permitemo“desenho”da
imagem(atravésdainvocaçãoaométodoDrawImage)
ParaoefeitotemosquecriarumanovaclassequeherdadeJPanel,
invocandoossuperconstrutoresereescrevendoométodopaintComponent
ouométodopaint
Integração de imagens (frames-JPanel)

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 36
Inserçãodeimagemnumpainel:opção1,reescreverométodopaint(Graphics)
importjava.awt.Color;importjava.awt.Cursor;
importjava.awt.Dimension;importjava.awt.Graphics;importjava.awt.Image;
importjavax.swing.JPanel;
publicclassMyImagePanelextendsJPanel{
publicImageimage;
publicMyImagePanel(intsizeX,intsizeY){
setPreferredSize(newDimension(sizeX,sizeY));
setBackground(Color.GRAY);
setCursor(newCursor(Cursor.HAND_CURSOR));
## }
publicvoidpaint(Graphicsg){
if(image!=null)
g.drawImage(image,1,1,this);
## }
publicImagegetImage(){
returnimage;
## }
publicvoidsetImage(Imageimage){
this.image=image;
## }
## }
Integração de imagens (frames-JPanel)

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 37
AocriaraclasseMyImagePanel(queherdadeJPanel)inclui-seométodo
setImage()parapermitirdefiniraimagemaassociaraopainel.Em
alternativa,poderiaserassociadaaimagematravésdoconstrutordaclasse.
## Paraintegrarimagensatravésdeumpainel,
1.cria-seumobjectoJPanel(1)usandoanossaclasse(que
implementaJPanel);
2.invoca-seométodosetImage()(2);
## 3.eporfiminvoca-seométodorepaint()(3)
Imageimage=Toolkit.getDefaultToolkit().getImage(image1.jpg);
MyImagePanelmyPanelDialog=newMyImagePanel(100,100);//1a
//JPanelmyPanelDialog=newMyImagePanel(100,100);//1b
myPanelDialog.setLayout(newFlowLayout());
myPanelDialog.setImage(image);//2
myPanelDialog.repaint();//3
Integração de imagens (frames-JPanel)

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 39
Semelhanteàopção1,foicriadaumaclasseMyImagePanel2(queherdade
JPanel).Comoexemploalternativo,aoinvésdedisponibilizarummétodo
setImage(),apassagemdoobjectoImageérealizadaatravésdoconstrutor
daclasse.
## Talcomonoexemploanterior,paraintegrarimagensatravésdeumpainel,
cria-seumobjectoImagePanel2passandooobjectoImagecomoargumento:
Imageimage=Toolkit.getDefaultToolkit().getImage(image1.jpg);
MyImagePanel2myPanelDialog=newMyImagePanel2(image);
myPanelDialog.setLayout(newFlowLayout());
Integração de imagens (frames-JPanel)

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 40
importjava.awt.Graphics;importjava.awt.Image;importjava.awt.LayoutManager;
importjavax.swing.ImageIcon;importjavax.swing.JPanel;
publicclassMyImagePanelextendsJPanel{
privateStringimageFile="/images/backgroundFile.jpg";
publicMyImagePanel(){
super();
## }
publicMyImagePanel(StringaImage){
super();
this.imageFile=aImage;
## }
publicMyImagePanel(LayoutManageraLayout){
super(aLayout);
## }
publicvoidpaintComponent(Graphicsg){
## /*createimageicontogetimage*/
ImageIconicon=newImageIcon(getClass().getResource(imageFile));
Imageimage=icon.getImage();
/*Drawimageonthepanel*/
super.paintComponent(g);
if(image!=null)
g.drawImage(image,0,0,getWidth(),getHeight(),this);
## }
## }
//Invocação(semdefinirimagem):JPanelmyPanel=newMyImagePanel(newFlowLayout());
//Invocação(definindoaimagem:JPanelmyPanel=newMyImagePanel("/images/backgroundFile.jpg");
Integração de imagens como background (frames-JPanel)

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 41
Resumindo,ainserçãodeimagensemframesimplicacarregaraimagemparaumobjectoImage:
Filefile=newFile(“images/image.jpg”);
Imageimage=Toolkit.getDefaultToolkit().getImage(file.toString());
## Paraumacessouniversalaoficheirodeimagem(analisadodeseguida):
## Imageimage=
Toolkit.getDefaultToolkit().getImage(
getClass().getResource(file.toString()));
Einseri-lonumacomponente.NocasodeumJPanelénecessáriocriarumanovaclassequeherde
deJPanelereescrevaométodopaint(Graphics)oupaintComponent(),parainvocarométodo
drawImage().
MyImagePanelmyPanelDialog=newMyImagePanel(100,100);
myPanelDialog.setImage(image);
myPanelDialog.repaint();
EmalternativaaousodeJPanel,podemosusaroutrascomponentes(ImagePanel,JButton,
JLabel)definidoorespectivoícone(ImageIcon):
JButtonmyButton=newJButton(newImageIcon(image));
JLabelmyLabel=newJlabel(newImageIcon(image));
Integração de imagens (frames-Resumo)

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 42
Elementos Multimédia em Frames: Ícones
## Elementos Multimédia

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 43
## Paraadicionarumaimagemcomoumíconeàaplicação,nocanto
superioresquerdodajanelaenabarradetarefasdevemosrecorrer
aométodosetIconImage()nomain()daaplicação:
publicstaticvoidmain(String[] args){
JFramejanela= new NomeClasse();
janela.setIconImage(
Toolkit.getDefaultToolkit().getImage("Image.jpg"));
## (...)
## }
## Oudeformauniversal:
janela.setIconImage(
Toolkit.getDefaultToolkit().getImage(getClass().getResource(“image.jpg”)));
Integração de ícones na aplicação

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 44
## Comovimos,paraintegrarmosimagensnumaaplicaçãoécomumrecorrer
aoobjectoImageIconsobreumelementográfico(e.g.JLabel)
StringiconPath="images/logo.png";
JPanelpanelImage=newJPanel(newBorderLayout());
JLabellabelIcon=newJLabel(newImageIcon(iconPath));
//labelIcon.setIcon(newImageIcon(iconPath));
labelIcon.setHorizontalAlignment(JLabel.LEFT);
panelImage.add(labelIcon);
## Alternativa:
ImageIconicon=createImageIcon("images/myFile.gif","LoremIpsum");
myLabel1=newJLabel("ImageandText",icon,JLabel.CENTER);
## ...
myLabel3=newJLabel(icon);
## Ocompiladorvaiprocurarosficheiros(imagens)àdirectoria/imagesque
deverásercriadanaraizdoprojecto(emeclipse)
Nestecasonãoépossívelintegrarosficheiros(imagens)emficheirosJAR
Integração de imagens através de ícones

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 45
Aoincluirumaimagemnumelemento(e.g.JLabel,JButton)otextodesse
elementodeixadeservisível,umavezqueénecessáriodefinira
posiçãodotextorelativamenteaoelemento,atravésdosmétodos
—setHorizontalTextPosition()e
—setVerticalTextPosition()
Existemváriosmétodos,sendooseguinteomaissimples.Porexemplo,
paraumaJLabel:
ImagemyImage=
Toolkit.getDefaultToolkit().getImage("images/myFile.jpg");
JLabelmyLabel=newJLabel();
myLabel.setIcon(newImageIcon(myImage));
myLabel.setText(“LoremIpsum”);
myLabel.setHorizontalTextPosition(JLabel.CENTER);
myLabel.setVerticalTextPosition(JLabel.CENTER);
myLabel.setBorder(BorderFactory.createLineBorder(Color.black));
container.add(myLabel);
Integração de imagens através de ícones

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 47
ParaocasodeumJButton,procede-sedeigualformaparaincluiruma
imagem(comoícone)etextosobreessaimagem,definindoaposiçãodo
textorelativamenteàimagem:
ImagemyImage=
Toolkit.getDefaultToolkit().getImage("images/myFile.jpg");
JButtonmyButton=newJButton(newImageIcon(myImage));
myButton.setText(“LoremIpsum”);
myButton.setHorizontalTextPosition(JLabel.CENTER);
myButton.setVerticalTextPosition(JLabel.CENTER);
myButton.addActionListener(this);
container.add(myButton);
Integração de imagens através de ícones

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 48
## Emalternativaaousodeimagensparaícones,podemos“desenhar”osnossos
ícones,definindoaclasseColorIcon:
publicclassColorIconimplementsIcon{
privateColorcolor;
privateintwidth;
privateintheight;
publicColorIcon(Colorcolor,intwidth,intheight){
this.color=color;
this.width=width;
this.height=height;
## }
publicintgetIconWidth(){
returnwidth;
## }
publicintgetIconHeight(){
returnheight;
## }
publicvoidpaintIcon(Componentc,Graphicsg,intx,inty){
g.setColor(color);
g.fillRect(x,y,width,height);
## }
## }
Invocação:JLabelmyLabel=newJLabel(newColorIcon(Color.YELLOW,200,150));
Integração de imagens através de ícones

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 49
## Comoindicadoanteriormente,paraincluirficheiros(recursos)deum
projectoemficheiroJAR(porexemploimagensqueaaplicação
necessita)énecessáriorecorreràpropriedadeclass.getResource()
## Estapodeserformadadeduasformas:
getClass().getResource(“/images/picture1.png”)
NomeDaClasse.class.getResource(“/images/picture1.png”);
## Nestecasoocompiladorvaiprocurarosficheiros(imagens)àdirectoria
## /imagesquedeverásercriadanadirectoria/binou/srcdoprojecto(em
eclipse)enãonaraizdoprojecto
## Éassimpossívelpartilhar/incluirprojectoserecursos(ficheiros,como
imagens)atravésdeJARs
## Atençãoqueonomedadirectoriacomeçacomosímbolo“/”,ouseja
## “/images/picture1.png”enão“images/picture1.png”comoanteriormente
Acesso universal a ficheiros (de imagens)

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 50
Apropriedadeclass.getResource()devolveumobjectojava.net.URL,
quepodeserusadoparacriarumobjectoImagequerepresentaa
imagemimportada
EsseobjectoImagepodedepoisseratribuídocomoíconedeum
elementográfico(e.g.JPanel)
StringiconPath="/images/nu_fulllogo_resized.png";
JPanelpanelImage=newJPanel(newBorderLayout());
java.net.URLurl=NomeDaClasse.class.getResource(iconPath);
Imageimage=Toolkit.getDefaultToolkit().getImage(url);
JLabellabelIcon=newJLabel(newImageIcon(image));
//labelIcon.setIcon(newImageIcon(iconPath));
labelIcon.setHorizontalAlignment(JLabel.LEFT);
panelImage.add(labelIcon);
Acesso universal a ficheiros (de imagens)

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 51
## Emalternativaàinstrução
java.net.URLurl=NomeDaClasse.class.getResource(iconPath);
Imageimage=Toolkit.getDefaultToolkit().getImage(url);
quedevolveoobjectoURLusadocomoargumentoparaaobtençãoda
imagem,podemosformarumaúnicainstrução:
## Imageimage=
Toolkit.getDefaultToolkit().getImage(getClass().getResource(iconPath));
Acesso universal a ficheiros (de imagens)

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 52
## Actualizandooexemploanteriorobtemosaseguinteinstrução:
JPanelpanelImage=newJPanel(newBorderLayout());
## Imageimage=
Toolkit.getDefaultToolkit().getImage(
getClass().getResource(iconPath)
## );
JLabellabelIcon=newJLabel(newImageIcon(image));
labelIcon.setHorizontalAlignment(JLabel.LEFT);
panelImage.add(labelIcon);
Acesso universal a ficheiros (de imagens)

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 53
## Outrapossibilidadeparaainserçãodeimagensérecorreraíconesque
podemserassociadosacaixasdetexto,botões,ououtroselementos.
ParaoefeitovamosrecorreràclasseImageIcon:
## (...)
ImageIconicone=newImageIcon(“imagem.jpg”);
JButtonbotao=newJButton(“Informaçãosobreobotão”);
botao.setBorder(newTitledBorder("Info"));
botao.setIcon(icone);
## (...)
## Alternativa:
## (...)
## Iconicone=
newImageIcon(getImage(getDocumentBase(),“imagem.jpg”));
JButtonbotao=
newJButton(“Informaçãosobreobotão”,icone);
## (...)
Integração de imagens através de ícones

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 54
## Parainseriraaplicaçãocomoumprocessonabarradetarefasdo
sistema(systemtray)podemosrecorreràclasse
java.awt.SystemTray
## Épossívelacederàbarradetarefasatravésdométodo
SystemTray.getSystemTray(),
apósverificarseafuncionalidadeésuportadapelosistema,atravésdo
método
SystemTray.isSupported()
ÉpossívelentãoinserirouremoverobjectosTrayIcons
‒Adicionar:add(java.awt.TrayIcon
‒Remover:remove(java.awt.TrayIcon)
Integração de ícones na aplicação

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 55
## Podemosposteriormenteconfiguraroobjectonabarradetarefaspara
introduzirumaimagem
trayIcon.setImage(updatedImage);
inserirumatooltipsemprequeoratoentranasuaáreadeacção
trayIcon.setTooltip("I'mbusy.Goaway.");
epermitirqueaimagemseadaptaaoespaçodisponível
automaticamente
trayIcon.setImageAutoSize(true);
Integração de ícones na aplicação

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 56
finalTrayIcontrayIcon;
if(SystemTray.isSupported()){
SystemTraytray=SystemTray.getSystemTray();
Imageimage=Toolkit.getDefaultToolkit().getImage("tray.gif");
MouseListenermouseListener=newMouseListener(){
publicvoidmouseClicked(MouseEvente){
System.out.println("TrayIcon-Mouseclicked!");
## }
publicvoidmouseEntered(MouseEvente){
System.out.println("TrayIcon-Mouseentered!");
## }
publicvoidmouseExited(MouseEvente){
System.out.println("TrayIcon-Mouseexited!");
## }
publicvoidmousePressed(MouseEvente){
System.out.println("TrayIcon-Mousepressed!");
## }
publicvoidmouseReleased(MouseEvente){
System.out.println("TrayIcon-Mousereleased!");
## }
## };
## //(...)
Integração de ícones na aplicação

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 57
## //(...)
ActionListenerexitListener=newActionListener(){
publicvoidactionPerformed(ActionEvente){
System.out.println("Exiting...");
## System.exit(0);
## }
## };
PopupMenupopup=newPopupMenu();
MenuItemdefaultItem=newMenuItem("Exit");
defaultItem.addActionListener(exitListener);
popup.add(defaultItem);
trayIcon=newTrayIcon(image,"TrayDemo",popup);
ActionListeneractionListener=newActionListener(){
publicvoidactionPerformed(ActionEvente){
trayIcon.displayMessage("ActionEvent",
"AnActionEventHasBeenPerformed!",TrayIcon.MessageType.INFO);
## }
## };
trayIcon.setImageAutoSize(true);
trayIcon.addActionListener(actionListener);
trayIcon.addMouseListener(mouseListener);
try{
tray.add(trayIcon);
}catch(AWTExceptione){
System.err.println("TrayIconcouldnotbeadded.");
## }
}else{//SystemTrayisnotsupported}
Integração de ícones na aplicação

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 58
Elementos Multimédia em Frames: Sons
## Elementos Multimédia

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 59
Desdeasprimeirasversões(JDK1.0)queabibliotecaJavadisponibiliza
formassimplificadasparaleituradeficheirosáudio,atravéspackage
sun.audio.*:
-AudioStream:permitecarregarficheirosáudio
-AudioPlayer:permitecontrolaraexecuçãodamúsica
## Paraoefeitocarregamosoficheiroáudiodosistemadeficheiro
(InputStream),queseráprocessadopelaclasseAudioStreameasua
execuçãoficaacargodaclasseAudioPlayer:
InputStreamin=newFileInputStream("C:\\myAudioFile.wav");
AudioStreamas=newAudioStream(in);
AudioPlayer.player.start(as);
Integração de sons (frame)

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 60
## Adicionalmente,apackagesun.audio,disponibilizaclassescomo
ContinuousAudioDataStreameAudioDataquepermitemummaiorcontrolodos
ficheirosdeáudio,nomeadamenteoloopcontínuodamúsica
InputStreamin=newFileInputStream("C:\\myAudioFile.wav");
AudioStreamas=newAudioStream(in);
AudioDatamusicData=as.getData();
ContinuousAudioDataStreamloop=
newContinuousAudioDataStream(musicData);
AudioPlayermusicPlayer=AudioPlayer.player;
musicPlayer.start(loop);
## Defrisarqueestasclassesapenasconseguemmanipularficheirosdeáudio
nãocomprimidos(e.g.wav,au,oggVorbis),nãosuportandocompressões
comoMP3.
AOracle/SundisponibilizaramposteriormenteaJavaMediaFramework
(https://www.oracle.com/java/technologies/javase/java-media-framework.html),quejá
permitesuporteparaformatosadicionais,comoMP3
Alternativamente,pode-serecorrerabibliotecasexternas,comooJLayer,
BasicPlayerAPIouJACoMP3Player
Integração de sons (frame)

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 61
## Posteriormente,apackagejavax.sound.sampledintroduziunovasclasses
paraummaiorcontrolo:AudioInputStream,AudioSystemeClip:
InputStreamin=newFileInputStream("C:\\myAudioFile.wav");
AudioInputStreamas=AudioSystem.getAudioInputStream(in);
Clipclip=AudioSystem.getClip();
if(clip!=null){
clip.open(as);
clip.loop(Clip.LOOP_CONTINUOUSLY);
clip.start();
## }
AclasseClipdisponibilizaváriasacçõesdecontrolo,nomeadamenteopen,
start,stop,loopeclose.
ParaencerraroobjectoClip:
clip.stop();
clip.close();
clip=null;
Integração de sons (frame)

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 62
AplataformaJavaFXfoidesenvolvidaparaacriaçãodeaplicaçõesricasem
conteúdosmultimédiaparaaInternet.ContemasclassesMediae
MediaPlayerquefornecemmaisfuncionalidadesparacontrolarficheiros
áudio,especialmenteficheirocodificados,comooMP3:
Mediahit=newMedia(newFile(“som.mp3”).toURI().toString());
MediaPlayermediaPlayer=newMediaPlayer(hit);
mediaPlayer.play();
## Sendonecessáriorealizarasimportaçõescorrectas:
importjavafx.scene.media.Media;
importjavafx.scene.media.MediaPlayer;
Integração de sons (frame)

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 63
## Elementos Multimédia
(Imagens, Ícones, Sons)
em Applets
## Elementos Multimédia

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 64
ÉpossívelnumaJAppletintegrarmosimagensemformato*.gifou
## *.jpg.
EmApplets,basicamentetemosquealocarespaçonamemóriaparaa
imagematravésdométodogetImage()nassuasdiversasvariantes:
Imagefig=getImage(
newURL(“http://localhost/Ficheiros/imagem.gif"));
Imagefig=getImage(getDocumentBase(),"imagem.gif");
Imagefig=getImage(getCodeBase(),"imagem.gif");
Imagefig=getImage(“c://ficheiros//imagem.gif");
enquantonumaframeseria
fig=Toolkit.getDefaultToolkit().getImage(filenameOrURL);
OsmétodosgetDocumentBase()egetCodeBase()sãométodosdefinidos
nasclassesAppleteJAppletquedevolvem,respectivamente,odirectório
ondeseencontraodocumentoHTMLquecontémaappleteodirectório
ondeseencontraoficheirodaApplet
Integração de imagens (applets)

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 65
OmétodogetCodeBase()serveparalocalizaroficheirodaimageme
deveserusadoquandoesteestánamesmalocalizaçãodoficheirode
extensãojava,ousejaoficheiroquecontémocódigodoprograma
Émaiscomum,noentanto,usarométodogetDocumentBase()quevai
localizaroficheirodaimagemaosítioondeestáoficheirodeextensão
html.
## Alternativamentepodemosindicaronomedoficheirodaimagemcomo
caminhocompleto.NestecasoométodogetImage()sótemesse
parâmetro
OmétodoquecarregaedesenhaaimageméométododrawImage()
HáváriosmétodosdrawImage(),comdiferentesparâmetros(method
overloading),permitindoousadoacimadesenharaimagemnaposiçãox,y
indicadaecomotamanhoporomissãodaimagem
OutrosmétodosdrawImage()permitem,alémdisso,escalonaraimagem
aodesenhá-la
Integração de imagens (applets)

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 66
importjavax.swing.*;
importjava.awt.*;
importjava.awt.event.*;
publicclassImagextendsJApplet{
privateImagefig;
publicvoidinit(){
fig=getImage(getCodeBase(),"imagem.gif");
## }
publicvoidpaint(Graphicsg){
booleanb=g.drawImage(fig,20,20,this);
## }
## }
Integração de imagens (applets)

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 67
TambémépossívelnumaJApplettocarmossonsdesdequeos
ficheirosondeestãopré-gravadospossuamoformato*.AU
## Ométodoquepermitetocarumsoméométodoplay()etem
comoparâmetrosalocalizaçãoeonomedoficheirodosom
play(newURL(“http://localhost/Ficheiros/ding.au"));
play(getDocumentBase(),"ding.au");
play(getCodeBase(),"ding.au");
play(”c://ficheiros//ding.au");
## Talcomoparaasimagens,háoutrométodoplay()sócomum
parâmetroqueéonomedoficheirodesomcomocaminhocompleto
Ocódigoseguintepermite-noscriarumaAppletcomumbotão
que,aoserpremido,tocaumsom
Integração de sons (applet)

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 68
importjavax.swing.*;
importjava.awt.*;
importjava.awt.event.*;
publicclassSom extendsJAppletimplementsActionListener{
privateContainercont;
privateJButtonb;
publicvoidinit(){
cont= getContentPane(); // Obtém o contentpane da applet
cont.setLayout(newFlowLayout());
b = newJButton("Toca");    // Cria botão
cont.add(b);// Junta-o à applet
b.addActionListener(this); // Acrescenta objecto listener
## }
public void actionPerformed(ActionEvente){
play(getCodeBase(),"ding.au");
## }
## }
Integração de sons (applet)

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 69
NumaJFrameépossívelintegralclipsdesomatravésdaclasseAudioClip:
Audioclipsom=Applet.newAudioClip(getURL("dir//file.au"));
## Estaclassedisponibilizaosmétodosplay(),stop()eloop()para
controlodoclipdeáudio
NadefiniçãoanterioréusadoométodogetURL()quetemcomofunção
obteralocalizaçãocorrectadeumdeterminadoficheiro:
URLgetURL(StringpathnameRelativo){
URLcodeBase,completeURL=null;
try{
codeBase=newURL("file:"+
System.getProperty("user.dir")+"/");
completeURL=newURL(codeBase,pathnameRelativo);
}catch(MalformedURLExceptione){
System.err.println(e.getMessage());returnnull;
## }
returncompleteURL;
## }
Integração de sons (frame)

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 70
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

Interfaces Gráficos –Caixas de Diálogo, Menus e Elementos Multimédia
Marco Veloso –Programação Aplicada
## 71
“TheDefinitiveGuidetoJavaSwing",3ªEdição
JohnZukowski
Apress,ISBN:1590594479
"FundamentosdeProgramaçãoemJava2",Capítulo12“InterfacesGráficos”
AntónioJoséMendes,MariaJoséMarcelino
## FCA,ISBN:9727224237
## Referências