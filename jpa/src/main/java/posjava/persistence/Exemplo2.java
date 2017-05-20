package posjava.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import posjava.persistence.entities.Empregado;
import posjava.persistence.entities.Garagem;

public class Exemplo2 {
	public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("posjavaPU");
        EntityManager em = emf.createEntityManager();

        // --- INICIO DA TRANSAÇÃO
        EntityTransaction tx1 = em.getTransaction();
        tx1.begin();
        
        Empregado empregado1 = new Empregado();
        em.persist(empregado1);
        
        Garagem garagem1 = new Garagem();
        garagem1.setEmpregado(empregado1);
        em.persist(garagem1);
        
        Garagem garagem2 = new Garagem();
        em.persist(garagem2);
        
        Garagem garagem3 = new Garagem();
        em.persist(garagem3);
        
        Garagem garagem4 = new Garagem();
        em.persist(garagem4);
        
        Garagem garagem5 = new Garagem();
        em.persist(garagem5);
        
        Garagem garagem6 = new Garagem();
        em.persist(garagem6);
        
        Garagem garagem7 = new Garagem();
        em.persist(garagem7);
        
        Garagem garagem8 = new Garagem();
        em.persist(garagem8);
        
        Garagem garagem9 = new Garagem();
        em.persist(garagem9);
        
        Garagem garagem10 = new Garagem();
        em.persist(garagem10);

        //criar 10 garagens
        //criar 05 projetos
        //criar 04 departamentos
        //criar 08 empregados
        
        //associar
        //atribuir uma garagem para cada empregado
        //adicionar cada empregado a um departamento
        //adicionar cada empregado a um projeto

        tx1.commit();
        
	}

}
