public interface Criteria {
    public boolean corresponds(Flat x);
}

public class CriteriaPrice implements Criteria {
    private int maxPrice;

    public void CriteriaPrice(int maxPrice){
        this.maxPrice = maxPrice;
    }

    @Override
    public boolean corresponds(Flat x){
        return x.getRent < this.maxPrice;
    }
}

public class CriteriaRooms implements Criteria {
    private int minRooms;

    public void CriteriaRooms(int minRooms){
        this.minRooms = minRooms;
    }

    @Override
    public boolean corresponds(Flat x){
        return x.getNumberOfRooms > this.minRooms;
    }
}

public class Agency {
    private List<Flat> flats;

    //construct and other methods

    public Flat<Flat> selection (Criteria criteria){
        List<Flat> flatSelected = new ArrayList<Flat>();

        for(Flat flat : flats){
            if(criteria.corresponds(flat)){
                flatSelected.add(flat);
            }
        }
        return flatSelected;
    }    
}

//4- The appropriate design model for designing Complex Criteria is the Composite Pattern. 
//This pattern is used to group a set of criteria and process them as a single set.
public class ComplexCriteria implements Criteria{
    private List<Criteria> criteriaList;

    public complexCriteria (List<Criteria> criteriaList){
        this.criteriaList = criteriaList;
    }

    @Override
    public boolean corresponds(Flat flat){
        for(Criteria criteria : criteriaList){
            if(!criteria.corresponds(flat)){
                return false;
            }
        }
        return true;
    }
}


Agency agency = new Agency();
// Initialize and add flats to the agency

Criteria criteriaRooms = new criteriaRooms(4);
Criteria criteriaPrice = new criteriaPrice(25000);

ComplexCriteria complexCriteria = new ComplexCriteria(Arrays.asList(criteriaRooms, criteriaPrice));

List<Flat> selectedFlats = agency.selection(complexCriteria);

