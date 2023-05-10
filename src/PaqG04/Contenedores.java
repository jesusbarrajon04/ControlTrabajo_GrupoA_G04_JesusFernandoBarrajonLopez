package PaqG04;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Contenedores extends JFrame{
    private JButton mapaButton;
    private JButton desapilarButton;
    private JButton mostrarDatosButton;
    private JButton apilarButton;
    private JTextField id;
    private JTextField Tpeso;
    private JTextField Tdescripcion;
    private JTextField Tempresaenvia;
    private JTextField Tempresarecibe;
    private JTextField textField8;
    private JTextField texto2;
    private JPanel Contenedores;
    private JButton contenedorPorPaisButton;
    private JTextPane textPane1;
    private JCheckBox checkPrueba;
    private JTextField Tcolumna;
    private JRadioButton prioridad1;
    private JRadioButton prioridad2;
    private JRadioButton prioridad3;
    private JComboBox campoPais;
    private JRadioButton nHub0;
    private JRadioButton nHub1;
    private JRadioButton nHub2;
    private Puerto Valencia;

    Contenedores(){
        setTitle("Contenedores");
        setSize(1050,750);
        setDefaultCloseOperation(JInternalFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setContentPane(Contenedores);
        //Creamos el hub con el que vamos a trabajar
        try{
            FileInputStream fis=new FileInputStream("puerto.dat");
            ObjectInputStream entrada=new ObjectInputStream(fis);
            Valencia = (Puerto) entrada.readObject(); //es necesario el casting
            fis.close(); // no es obligatorio pero es recomendable
            entrada.close(); // no es obligatorio pero es recomendable
        }catch (Exception e){
//Si el fichero no existe y aparece un error se crea el Puerto con el constructor por defecto
            Valencia = new Puerto();
        }

        apilarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //Pasamos los valores escritos a manos en la interfaz a su formato correspondiente
                int idCampo = Integer.parseInt(id.getText());
                double pesoCampo=Double.parseDouble(Tpeso.getText());
                //cojo el país del combo
                String paisCampo=campoPais.getSelectedItem().toString();
                boolean aduanasCampo = checkPrueba.isSelected();
                //compruebo el botón de la prioridad que está pulsado, si ningún botón está pulsado la prioridad será 3
                int prioridad=3;
                if(prioridad1.isSelected()){
                    prioridad= 1;
                }else if(prioridad2.isSelected()){
                    prioridad=2;
                }
                String descripcionCampo=Tdescripcion.getText();
                String empresaEnviaCampo=Tempresaenvia.getText();
                String empresaRecibeCampo=Tempresarecibe.getText();

                Contenedor contenedor=new Contenedor(idCampo,pesoCampo,paisCampo,aduanasCampo,prioridad,descripcionCampo,empresaEnviaCampo,empresaRecibeCampo);
                textPane1.setText(Valencia.apilar(contenedor));
                //Borramos los campos (preguntar mañana en clase)
                id.setText("");
                Tpeso.setText("");
                prioridad1.setSelected(false);
                prioridad2.setSelected(false);
                prioridad3.setSelected(false);
                Tdescripcion.setText("");
                Tempresaenvia.setText("");
                Tempresarecibe.setText("");
                FileOutputStream fos = null;
                ObjectOutputStream salida = null;
                try {
                    fos = new FileOutputStream("puerto.dat");
                    salida = new ObjectOutputStream(fos);
                    salida.writeObject(Valencia);
                    fos.close();
                    salida.close();
                } catch (Exception ex) {
                    // si aparece un error se muestra en pantalla el tipo de error
                    ex.printStackTrace();
                }

            }
        });
        mapaButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                textPane1.setText(Valencia.toString());
            }
        });
        mostrarDatosButton.addMouseListener( new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //Para mostrar los datos de un contenedor, cogemos el id del campo Id y llamamos a la función del hub
                int idCampo = Integer.parseInt(id.getText());
                int nHub=2;
                if(nHub0.isSelected()){
                    nHub=0;
                }else if(nHub1.isSelected()){
                    nHub=1;
                }
                textPane1.setText(Valencia.mostrarDatos(nHub,idCampo));
                id.setText("");
                nHub0.setSelected(false);
                nHub1.setSelected(false);
                nHub2.setSelected(false);
            }
        });
        contenedorPorPaisButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //Para calcular el número de contenedores de un país, cogemos un país del combo y llamamos a la función
                //del hub.
                int nHub=2;
                if(nHub0.isSelected()){
                    nHub=0;
                }else if(nHub1.isSelected()){
                    nHub=1;
                }
                textPane1.setText(Valencia.calcularNumeroContenedoresDeUnPais(nHub,campoPais.getSelectedItem().toString()));
                nHub0.setSelected(false);
                nHub1.setSelected(false);
                nHub2.setSelected(false);
            }
        });
        desapilarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //Paso columna string a int
                int columnaCampo=Integer.parseInt(Tcolumna.getText());
                int nHub=2;
                if(nHub0.isSelected()){
                    nHub=0;
                }else if(nHub1.isSelected()){
                    nHub=1;
                }
                //Llamo a desapilar
                textPane1.setText(Valencia.desapilar(nHub,columnaCampo));
                //Dejo campo vacío
                Tcolumna.setText("");
                nHub0.setSelected(false);
                nHub1.setSelected(false);
                nHub2.setSelected(false);
                FileOutputStream fos = null;
                ObjectOutputStream salida = null;
                try {
                    fos = new FileOutputStream("puerto.dat");
                    salida = new ObjectOutputStream(fos);
                    salida.writeObject(Valencia);
                    fos.close();
                    salida.close();
                } catch (Exception ex) {
                    // si aparece un error se muestra en pantalla el tipo de error
                    ex.printStackTrace();
                }
            }
        });
        //Hacemos los listener de los botones de la prioridad para que no se pueda seleccionar más de uno a la vez
        prioridad1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(prioridad1.isSelected()){
                    prioridad2.setSelected(false);
                    prioridad3.setSelected(false);
                }
            }
        });
        prioridad2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(prioridad2.isSelected()){
                    prioridad1.setSelected(false);
                    prioridad3.setSelected(false);
                }
            }
        });
        prioridad3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(prioridad3.isSelected()){
                    prioridad2.setSelected(false);
                    prioridad1.setSelected(false);
                }
            }
        });
        nHub0.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(nHub0.isSelected()){
                    nHub1.setSelected(false);
                    nHub2.setSelected(false);
                }
            }
        });
        nHub1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(nHub1.isSelected()){
                    nHub2.setSelected(false);
                    nHub0.setSelected(false);
                }
            }
        });
        nHub2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(nHub2.isSelected()){
                    nHub1.setSelected(false);
                    nHub0.setSelected(false);
                }
            }
        });
    }
    public static void main(String[] args) {
        Contenedores contenedores=new Contenedores();
    }
}
