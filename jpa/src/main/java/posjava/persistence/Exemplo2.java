package posjava.persistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import posjava.persistence.entities.Departamento;
import posjava.persistence.entities.Empregado;
import posjava.persistence.entities.Garagem;
import posjava.persistence.entities.Projeto;

public class Exemplo2 {
	public static void main(String[] args) {
		List<String> departamentos = Arrays.asList("Dep 1", "Dep 2", "Dep 3", "Dep 4");
		List<String> projetos = Arrays.asList("Proj 1", "Proj 2", "Proj 3", "Proj 4", "Proj 5");
		List<String> garagens = Arrays.asList("G1", "G2", "G3", "G4", "G5", "G6", "G7", "G8", "G9", "G10");
		List<String> empregados = Arrays.asList("Empregado 1", "Empregado 2", "Empregado 3", "Empregado 4", "Empregado 5", "Empregado 6", "Empregado 7", "Empregado 8");

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("posjavaPU");
        EntityManager em = emf.createEntityManager();

        EntityTransaction exCria = em.getTransaction();
        exCria.begin();
        //criar 04 departamentos
        criarDepartamento(departamentos, em);
        //criar 05 projetos
        criarProjeto(projetos, em);
    	//criar 10 garagens
        criarGaragem(garagens, em);
    	//criar 08 empregados
        criarEmpregado(empregados, em);
        exCria.commit();
       
        EntityTransaction exAssocia = em.getTransaction();
        exAssocia.begin();
        
        Query buscaEmpregado = em.createQuery("from Empregado e");
        List<Empregado> empregadosList = buscaEmpregado.getResultList();
    	
        //atribuir uma garagem para cada empregado
        atribuirGaragemParaEmpregado(em, empregadosList);
        
    	//adicionar cada empregado a um departamento
        atribuirDepartamentoParaEmpregado(em, empregadosList, departamentos);
        
        //adicionar cada empregado a um projeto
        atribuirProjetosParaEmpregado(em, empregadosList, projetos);
        
        exAssocia.commit();
        
	}

	private static void criarDepartamento(List<String> departamentos, EntityManager em) {
        departamentos.forEach(departamento -> {
        	Departamento dep = new Departamento(departamento);
        	em.persist(dep);
        });
	}
	
	private static void criarProjeto(List<String> projetos, EntityManager em) {
		projetos.forEach(projeto -> {
			Projeto proj = new Projeto(projeto);
			em.persist(proj);
		});
	}
	
	private static void criarGaragem(List<String> garagens, EntityManager em) {
		garagens.forEach(garagem -> {
			Garagem gar = new Garagem(garagens.indexOf(garagem), garagem);
			em.persist(gar);
		});
	}

	private static void criarEmpregado(List<String> empregados, EntityManager em) {
		empregados.forEach(empregado -> {
			Empregado emp = new Empregado(empregado, (long) (Math.random() * 1000));
			em.persist(emp);
		});
	}
	
	private static Garagem retornaGaragemPorNumero(double numero, EntityManager em) {		
		Query buscaGaragem = em.createQuery("from Garagem g where g.numero = :numero")
        		.setParameter("numero", numero);
		
		Garagem garagem = (Garagem) buscaGaragem.getSingleResult();
		return garagem;
	}
	
	private static Departamento retornaDepartamentoPorNome(String nome, EntityManager em) {		
		Query buscaDepartamento = em.createQuery("from Departamento d where d.nome = :nome")
        		.setParameter("nome", nome);
		
		Departamento departamento = (Departamento) buscaDepartamento.getSingleResult();
		return departamento;
	}
	
	private static Collection<Projeto> retornaProjetoPorNome(List<String> nome, EntityManager em) {	
		String nomeProjeto = "";
		for(String n : nome) {
			nomeProjeto += ", '" + n + "'";
		};
		
		nomeProjeto = nomeProjeto.substring(1, nomeProjeto.length());
		
		Query buscaProjeto = em.createQuery("from Projeto p where p.nome in (" + nomeProjeto + ")");
		
		Collection<Projeto> projeto = (Collection<Projeto>) buscaProjeto.getResultList();
		return projeto;
	}
	
	private static void atribuirGaragemParaEmpregado(EntityManager em,  List<Empregado> empregadosList) {
        empregadosList.forEach(empregado -> {
        	empregado.setGaragem(retornaGaragemPorNumero((Math.random() * 10), em));
        	em.persist(empregado);
        });
	}

	private static void atribuirDepartamentoParaEmpregado(EntityManager em, List<Empregado> empregadosList, List<String> departamentos) {
		Random r = new Random();
        empregadosList.forEach(empregado -> {
        	empregado.setDepartamento(retornaDepartamentoPorNome(departamentos.get(r.nextInt(departamentos.size())), em));
        	em.persist(empregado);
        });
	}
	
	private static void atribuirProjetosParaEmpregado(EntityManager em, List<Empregado> empregadosList, List<String> projetos) {
		Random r = new Random();
		empregadosList.forEach(empregado -> {
			List<String> projs = new ArrayList<>();
			for(int i = 0; i < 3; i ++) {
				projs.add(projetos.get(r.nextInt(projetos.size())));
			}
        	empregado.setProjetos(retornaProjetoPorNome(projs, em));
        	em.persist(empregado);
        });
	}

}
