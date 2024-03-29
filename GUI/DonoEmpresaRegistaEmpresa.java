package GUI;

import Entidades.Empresa;
import Entidades.TipoConsulta;
import Entidades.User;
import Exceptions.JaExisteEmpresaException;
import MetodosLogicos.DonoEmpresaMetodos;
import Repositorio.Repositorio;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DonoEmpresaRegistaEmpresa {
    private JButton buttonback;
    private JTextField textFieldTelefone;
    private JTextField textFieldNomeEmpresa;
    private JTextField textFieldNIFEmpresa;
    private JTextField textFieldNPorta;
    private JTextField textFieldLocalidade;
    private JTextField textFieldCodPostal;
    private JButton inserirButton;
    private JButton buttonlimpar;
    private JPanel panel;
    private JComboBox cb_addConsulta;

    public DonoEmpresaRegistaEmpresa(JFrame frame){
        String str;
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        LimparDados();
        voltarAtras(frame);
        inserirEmpresa(frame);
        for(TipoConsulta t: Repositorio.getInstance().getTiposConsultas()){
            str=t.getIdTipo()+"-"+t.getDescricao();
            cb_addConsulta.addItem(str);
        }
    }
    public void LimparDados(){
        buttonlimpar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textFieldLocalidade.setText(null);
                textFieldTelefone.setText(null);
                textFieldNIFEmpresa.setText(null);
                textFieldNomeEmpresa.setText(null);
                textFieldCodPostal.setText(null);
                textFieldNPorta.setText(null);
            }
        });
    }

    public void voltarAtras(JFrame frame){
        buttonback.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.setVisible(false);
                new DonoEmpresaRegistado(frame);
            }
        });
    }

    public void inserirEmpresa(JFrame frame){
        inserirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int telefone=0;
                int nif=0;
                int nPorta=0;

                String localidade=textFieldLocalidade.getText();
                try{
                    telefone=Integer.parseInt(textFieldTelefone.getText());
                }
                catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(null,"Não pode introduzir letras no telefone");
                }
                try{
                    nif=Integer.parseInt(textFieldTelefone.getText());
                }
                catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(null,"Não pode introduzir letras no nif");
                }
                try{
                    nPorta=Integer.parseInt(textFieldNPorta.getText());
                }
                catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(null,"Não pode introduzir letras no nº da porta");
                }
                String nomeEmpresa=textFieldNomeEmpresa.getText();
                String str=(String) cb_addConsulta.getSelectedItem();
                String [] out=str.split("-");
                int tipo=Integer.parseInt(out[0]);
                TipoConsulta tipoConsulta=DonoEmpresaMetodos.selectConsultaPorId(tipo);

                Empresa empresa= new Empresa(telefone,nomeEmpresa,nif,nPorta,localidade,Repositorio.getInstance().getCurrentUser().getUsername());
                empresa.setTipo(tipoConsulta);
                try{
                    DonoEmpresaMetodos.addEmpresa(Repositorio.getInstance().getEmpresas(), Repositorio.getInstance().getEmpresasLocalidade(), empresa);
                }
                catch (JaExisteEmpresaException ex){
                    JOptionPane.showMessageDialog(null,ex.getMessage());
                }
                JOptionPane.showMessageDialog(null,"Adicionou uma empresa com sucesso!!");
                panel.setVisible(false);
                new DonoEmpresaRegistado(frame);
            }
        });
    }
}
