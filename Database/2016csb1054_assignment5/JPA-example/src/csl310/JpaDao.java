/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csl310;

import csl310.jpa.Codepart;
import csl310.jpa.Post;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author auser
 */
public class JpaDao {
    private EntityManagerFactory emf = null;
    
    public JpaDao() {
        emf = Persistence.createEntityManagerFactory("CSL310-DB1PU");
    }
    
    public List<Codepart> findPostsByCodeSize(int min, int max) {
    	List<Codepart> list = null;
        EntityManager em = emf.createEntityManager();
        try {
            Query q = em.createNamedQuery("Codepart.findByCodesize");
            q.setParameter("min", min).setParameter("max", max);
            list = q.getResultList();
        } finally {
            em.clear();
            em.close();
        }
        return list;
    }

    public boolean postExists(long id) {
        boolean exists = false;
    	EntityManager em = emf.createEntityManager();
        try {
            Query q = em.createNamedQuery("Post.findById");
            q.setParameter("id", id);
            exists = q.getResultList().size() == 1;
        } finally {
            em.clear();
            em.close();
        }
        return exists;
    }

    public <E> void saveEntity(E entity) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            em.close();
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) {
        System.out.println("JPA test.");
        JpaDao dao = new JpaDao();
//        List<Codepart> cpl = dao.findPostsByCodeSize(1000, 1200);
//        System.out.println("Got "+cpl.size()+" records.");
//        for (Codepart c : cpl) {
//            System.out.println("Post text: " +c.getPostid().getCaTextpart());
//        }
        
        Post p = new Post();
        p.setId(9987651);
        p.setTitle("JPA TEST CSL310");
        dao.saveEntity(p);
        System.out.println("Saved new post.");
    }
}
