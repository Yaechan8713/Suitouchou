package reduce.project.yaerei.suitouchou;

import android.graphics.ColorSpace;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by yaerei on 2018/02/24.
 */

@Table(name = "peopleItem")
public class peopleItem extends Model {
    @Column(name = "peoplename")
    public String peoplename;

    public peopleItem(){
        super();
    }
}
