package posjava.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import posjava.persistence.entities.EmpregadoMixedAccess;
import posjava.persistence.entities.Todo;

public class Exemplo1 {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("posjavaPU");
        EntityManager em = emf.createEntityManager();

        // --- INICIO DA TRANSAÇÃO
        EntityTransaction tx1 = em.getTransaction();
        tx1.begin();

        Todo todo = new Todo("TODO 1", "primeiro TODO criado com jpa");
        em.persist(todo);

        Todo todo2 = new Todo("TODO 2", "Segundo TODO criado com jpa");
        em.persist(todo2);

        tx1.commit();
        // --- TERMINO DA TRANSAÇÃO

        // --- INICIO DA TRANSAÇÃO
        EntityTransaction tx2 = em.getTransaction();
        tx2.begin();

        Todo todo3 = new Todo("TODO 3", "terceiro TODO criado com jpa");
        em.persist(todo3);

        Todo todo4 = new Todo("TODO 4", "quarto TODO criado com jpa");
        em.persist(todo4);

        tx2.rollback();
        // --- TERMINO DA TRANSAÇÃO

        Query buscaTodo1 = em.createQuery("from Todo t");

        List< Todo > todos1 = buscaTodo1.getResultList();
        todos1.forEach(System.out::println);

        Todo todoX = em.find(Todo.class, 1l);
        System.out.println("Todo Retornado: " + todoX);

        // --- INICIO DA TRANSAÇÃO
        EntityTransaction tx3 = em.getTransaction();
        tx3.begin();
        
        Todo todoD = em.find(Todo.class, 1l);
        System.out.println("Removendo o primeiro: " + todoD);        
        em.remove(todoD);
        
        tx3.commit();
        
        List< Todo > todos2 = buscaTodo1.getResultList();
        todos2.forEach(System.out::println);
        
        //exemplo utilizando acesso misto
        
        EntityTransaction tx4 = em.getTransaction();
        tx4.begin();
        
        EmpregadoMixedAccess empMixed1 = new EmpregadoMixedAccess();
        empMixed1.setTelefone("44123456789");
        em.persist(empMixed1);
        
        EmpregadoMixedAccess empMixed2 = new EmpregadoMixedAccess();
        empMixed2.setTelefone("987654321");
        em.persist(empMixed2);
        
        tx4.commit();
        
        Query buscaEmpMix = em.createQuery("select e from EmpregadoMixedAccess e");
        List<EmpregadoMixedAccess> empMixs = buscaEmpMix.getResultList();
        
        empMixs.forEach(e -> {
        	System.out.println(String.format("ID %3d, Telefone %11s", e.getId(), e.getTelefone()));
        });
    }
}
