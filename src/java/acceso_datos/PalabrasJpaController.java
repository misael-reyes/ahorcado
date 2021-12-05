/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package acceso_datos;

import acceso_datos.exceptions.NonexistentEntityException;
import acceso_datos.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import modelo.Palabras;
import modelo.Tipopalabras;

/**
 *
 * @author Misael
 */
public class PalabrasJpaController implements Serializable {

    public PalabrasJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Palabras palabras) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Tipopalabras tipo = palabras.getTipo();
            if (tipo != null) {
                tipo = em.getReference(tipo.getClass(), tipo.getIdtipos());
                palabras.setTipo(tipo);
            }
            em.persist(palabras);
            if (tipo != null) {
                tipo.getPalabrasCollection().add(palabras);
                tipo = em.merge(tipo);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Palabras palabras) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Palabras persistentPalabras = em.find(Palabras.class, palabras.getIdpalabra());
            Tipopalabras tipoOld = persistentPalabras.getTipo();
            Tipopalabras tipoNew = palabras.getTipo();
            if (tipoNew != null) {
                tipoNew = em.getReference(tipoNew.getClass(), tipoNew.getIdtipos());
                palabras.setTipo(tipoNew);
            }
            palabras = em.merge(palabras);
            if (tipoOld != null && !tipoOld.equals(tipoNew)) {
                tipoOld.getPalabrasCollection().remove(palabras);
                tipoOld = em.merge(tipoOld);
            }
            if (tipoNew != null && !tipoNew.equals(tipoOld)) {
                tipoNew.getPalabrasCollection().add(palabras);
                tipoNew = em.merge(tipoNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = palabras.getIdpalabra();
                if (findPalabras(id) == null) {
                    throw new NonexistentEntityException("The palabras with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Palabras palabras;
            try {
                palabras = em.getReference(Palabras.class, id);
                palabras.getIdpalabra();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The palabras with id " + id + " no longer exists.", enfe);
            }
            Tipopalabras tipo = palabras.getTipo();
            if (tipo != null) {
                tipo.getPalabrasCollection().remove(palabras);
                tipo = em.merge(tipo);
            }
            em.remove(palabras);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Palabras> findPalabrasEntities() {
        return findPalabrasEntities(true, -1, -1);
    }

    public List<Palabras> findPalabrasEntities(int maxResults, int firstResult) {
        return findPalabrasEntities(false, maxResults, firstResult);
    }

    private List<Palabras> findPalabrasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Palabras.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Palabras findPalabras(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Palabras.class, id);
        } finally {
            em.close();
        }
    }

    public int getPalabrasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Palabras> rt = cq.from(Palabras.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    /**
     * método para buscar las palabras con base al tipo y nivel
     * seleccionado por el usuario
     * @param tipo
     * @param nivel
     * @return lista de palabras encontradas
     */
    public List<Palabras> findPalabras(int tipo, int nivel) {
        EntityManager em = getEntityManager();
        try {
            String query = "SELECT * FROM PALABRAS WHERE TIPO= "+tipo+" AND NIVEL="+nivel;
            Query qu = em.createNativeQuery(query, Palabras.class);
            return qu.getResultList();
        } finally {
            em.close();
        }
    }
    

}
