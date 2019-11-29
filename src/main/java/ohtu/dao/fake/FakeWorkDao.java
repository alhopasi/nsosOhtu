/*
 * @author londes
 */
package ohtu.dao.fake;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import ohtu.Work;
import ohtu.dao.Dao;

/**
 * A non-persistent placeholder for WorkDao Implemented as a singleton
 */
public class FakeWorkDao implements Dao<Work, Integer> {

    private static FakeWorkDao INSTANCE;
    private ArrayList<Work> works;

    public FakeWorkDao() {
        works = new ArrayList<>();
    }

    public static FakeWorkDao getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FakeWorkDao();
        }
        return INSTANCE;
    }

    /**
     * creates a copy of the object given to it and stores that into the list,
     * then updates the id on both the copy in use and the stored copy
     *
     * @param work the work to be stored in the database
     * @return
     */
    @Override
    public Work create(Work work) {
        Work stored = new Work(work.getAuthor(), work.getTitle(), work.getUrl(), work.getTags());
        Integer id = 0;
        if (!works.isEmpty()) {
            id = works.get(works.size() - 1).getId() + 1;
        }
        stored.setId(id);
        work.setId(id);
        works.add(stored);
        return work;
    }

    @Override
    public Work read(Integer key) {
        if (key < 0 || key > works.get(works.size() - 1).getId()) {
            return null;
        }
        Work stored = null;
        for (Work work : works) {
            if (Objects.equals(work.getId(), key)) {
                stored = work;
                break;
            }
        }
        if (stored != null) {
            Work copy = new Work(stored.getAuthor(), stored.getTitle(), stored.getUrl(), stored.getTags());
            copy.setId(stored.getId());
            return copy;
        } else {
            return null;
        }
    }

    @Override
    public Work update(Work work) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(Integer key) {
        if (key < 0 || key > works.get(works.size() - 1).getId()) {
            return false;
        }
        Work toRemove = null;
        for (Work work : works) {
            if (Objects.equals(work.getId(), key)) {
                toRemove = work;
                break;
            }
        }
        if (toRemove != null) {
            works.remove(toRemove);
            return true;
        }
        return false;
    }

    @Override
    public List<Work> list() {
        List<Work> ret = new ArrayList();
        for (Work stored : works) {
            Work copy = new Work(stored.getAuthor(), stored.getTitle(), stored.getUrl(), stored.getTags());
            copy.setId(stored.getId());
            ret.add(copy);
        }
        return ret;
    }

}
