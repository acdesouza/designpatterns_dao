package br.com.exemplodao.view;

import org.apache.log4j.Logger;

import br.com.commons.model.entity.BusinessException;
import br.com.exemplodao.model.entity.Cliente;


public class ExemploMain {
	private static Logger log = Logger.getLogger(ExemploMain.class);

	public static void main(String [] args){
		//Criar um cliente
		Cliente cliente = new Cliente();
		cliente.setNome("Antonio Carlos de Souza");
		cliente.setEndereco("Rua Lá em casa, número 100");
		cliente.setTelefone(null);
		
		try {
	        cliente.save();
        } catch (BusinessException e) {
	        log.error("Erro ao incluir o cliente.", e);
        }

        //Alterar o cliente criado
        Cliente clienteSalvo = Cliente.find(cliente.getId());
        clienteSalvo.setNome("Antonio Carlos da Graça Mota Durão de Souza");
        try {
        	clienteSalvo.save();
        } catch (BusinessException e) {
	        log.error("Erro ao alterar o cliente.", e);
        }

        //Remover o cliente criado
        try {
        	clienteSalvo.remove();
        } catch (BusinessException e) {
        	log.error("Erro ao excluir o cliente.", e);
        }
	}
}