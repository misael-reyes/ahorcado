/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package acceso_datos;

import acceso_datos.exceptions.NonexistentEntityException;
import acceso_datos.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Palabras;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import modelo.Tipopalabras;

/**
 *
 * @author Misael
 */
public class TipopalabrasJpaController implements Serializable {

    public TipopalabrasJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tipopalabras tipopalabras) throws RollbackFailureException, Exception {
        if (tipopalabras.getPalabrasCollection() == null) {
            tipopalabras.setPalabrasCollection(new ArrayList<Palabras>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Palabras> attachedPalabrasCollection = new ArrayList<Palabras>();
            for (Palabras palabrasCollectionPalabrasToAttach : tipopalabras.getPalabrasCollection()) {
                palabrasCollectionPalabrasToAttach = em.getReference(palabrasCollectionPalabrasToAttach.getClass(), palabrasCollectionPalabrasToAttach.getIdpalabra());
                attachedPalabrasCollection.add(palabrasCollectionPalabrasToAttach);
            }
            tipopalabras.setPalabrasCollection(attachedPalabrasCollection);
            em.persist(tipopalabras);
            for (Palabras palabrasCollectionPalabras : tipopalabras.getPalabrasCollection()) {
                Tipopalabras oldTipoOfPalabrasCollectionPalabras = palabrasCollectionPalabras.getTipo();
                palabrasCollectionPalabras.setTipo(tipopalabras);
                palabrasCollectionPalabras = em.merge(palabrasCollectionPalabras);
                if (oldTipoOfPalabrasCollectionPalabras != null) {
                    oldTipoOfPalabrasCollectionPalabras.getPalabrasCollection().remove(palabrasCollectionPalabras);
                    oldTipoOfPalabrasCollectionPalabras = em.merge(oldTipoOfPalabrasCollectionPalabras);
                }
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

    public void edit(Tipopalabras tipopalabras) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Tipopalabras persistentTipopalabras = em.find(Tipopalabras.class, tipopalabras.getIdtipos());
            Collection<Palabras> palabrasCollectionOld = persistentTipopalabras.getPalabrasCollection();
            Collection<Palabras> palabrasCollectionNew = tipopalabras.getPalabrasCollection();
            Collection<Palabras> attachedPalabrasCollectionNew = new ArrayList<Palabras>();
            for (Palabras palabrasCollectionNewPalabrasToAttach : palabrasCollectionNew) {
                palabrasCollectionNewPalabrasToAttach = em.getReference(palabrasCollectionNewPalabrasToAttach.getClass(), palabrasCollectionNewPalabrasToAttach.getIdpalabra());
                attachedPalabrasCollectionNew.add(palabrasCollectionNewPalabrasToAttach);
            }
            palabrasCollectionNew = attachedPalabrasCollectionNew;
            tipopalabras.setPalabrasCollection(palabrasCollectionNew);
            tipopalabras = em.merge(tipopalabras);
            for (Palabras palabrasCollectionOldPalabras : palabrasCollectionOld) {
                if (!palabrasCollectionNew.contains(palabrasCollectionOldPalabras)) {
                    palabrasCollectionOldPalabras.setTipo(null);
                    palabrasCollectionOldPalabras = em.merge(palabrasCollectionOldPalabras);
                }
            }
            for (Palabras palabrasCollectionNewPalabras : palabrasCollectionNew) {
                if (!palabrasCollectionOld.contains(palabrasCollectionNewPalabras)) {
                    Tipopalabras oldTipoOfPalabrasCollectionNewPalabras = palabrasCollectionNewPalabras.getTipo();
                    palabrasCollectionNewPalabras.setTipo(tipopalabras);
                    palabrasCollectionNewPalabras = em.merge(palabrasCollectionNewPalabras);
                    if (oldTipoOfPalabrasCollectionNewPalabras != null && !oldTipoOfPalabrasCollectionNewPalabras.equals(tipopalabras)) {
                        oldTipoOfPalabrasCollectionNewPalabras.getPalabrasCollection().remove(palabrasCollectionNewPalabras);
                        oldTipoOfPalabrasCollectionNewPalabras = em.merge(oldTipoOfPalabrasCollectionNewPalabras);
                    }
                }
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
                Integer id = tipopalabras.getIdtipos();
                if (findTipopalabras(id) == null) {
                    throw new NonexistentEntityException("The tipopalabras with id " + id + " no longer exists.");
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
            Tipopalabras tipopalabras;
            try {
                tipopalabras = em.getReference(Tipopalabras.class, id);
                tipopalabras.getIdtipos();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipopalabras with id " + id + " no longer exists.", enfe);
            }
            Collection<Palabras> palabrasCollection = tipopalabras.getPalabrasCollection();
            for (Palabras palabrasCollectionPalabras : palabrasCollection) {
                palabrasCollectionPalabras.setTipo(null);
                palabrasCollectionPalabras = em.merge(palabrasCollectionPalabras);
            }
            em.remove(tipopalabras);
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

    public List<Tipopalabras> findTipopalabrasEntities() {
        return findTipopalabrasEntities(true, -1, -1);
    }

    public List<Tipopalabras> findTipopalabrasEntities(int maxResults, int firstResult) {
        return findTipopalabrasEntities(false, maxResults, firstResult);
    }

    private List<Tipopalabras> findTipopalabrasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipopalabras.class));
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

    public Tipopalabras findTipopalabras(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tipopalabras.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipopalabrasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipopalabras> rt = cq.from(Tipopalabras.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
