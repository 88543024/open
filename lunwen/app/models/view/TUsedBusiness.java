package models.view;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import models.view.ComboBox;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
@Table(name = "T_USED_BUSINESS")
public class TUsedBusiness extends Model {
    @Required
    public String modelName;
    @Required
    public String business;

    public static List<String> getRealBusinessList(String modelName) {
        List<String> result = TUsedBusiness.find(
                "select distinct business from TUsedBusiness where modelName = '" + modelName + "' order by business").fetch();
        return result;
    }

    public static List<String> getModelList() {
        return TUsedBusiness.find("select distinct modelName from TUsedBusiness").fetch();
    }
}
