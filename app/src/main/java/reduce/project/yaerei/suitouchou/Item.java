package reduce.project.yaerei.suitouchou;

import android.graphics.ColorSpace;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import static android.R.attr.name;

/**
 * Created by yaerei on 2018/02/15.
 */

@Table(name = "Item")
public class Item extends Model {
    @Column(name = "name")
    public String name;

    public Item(){
        super();
    }
}
