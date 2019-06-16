package facade;

import dto.HistoryDTO;
import entity.History;
import exceptions.HistoryException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class HistoryFacade {

    private static EntityManagerFactory emf;
    private static HistoryFacade instance;

    public static HistoryFacade getInstance(EntityManagerFactory factory) {
        if (instance == null) {
            emf = factory;
            instance = new HistoryFacade();
        }
        return instance;
    }

    public void saveRequest(String week, String address) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            String arguments = "week:" + week + ",address:" + address;
            History history = new History(arguments);
            em.persist(history);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<HistoryDTO> getHistory() throws HistoryException {
        EntityManager em = emf.createEntityManager();
        List<HistoryDTO> historyDTOs = new ArrayList();
        try {
            List<History> histories = em.createNamedQuery("History.findAll", History.class).getResultList();
            if (histories.isEmpty()) {
                throw new HistoryException("The history of old requests is empty!");
            }
            for (History history : histories) {
                HistoryDTO dto = new HistoryDTO(history.getId(), history.getArguments());
                historyDTOs.add(dto);
            }
        } finally {
            em.close();
        }
        return historyDTOs;
    }
}
