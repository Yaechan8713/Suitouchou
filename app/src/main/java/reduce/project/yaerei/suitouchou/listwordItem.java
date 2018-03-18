package reduce.project.yaerei.suitouchou;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by yaerei on 2018/03/17.
 */

@Table(name = "listwordItem")
public class listwordItem extends Model {
    @Column(name = "listwordname")
    public String listwordname;

    public listwordItem(){
        super();
    }
}
