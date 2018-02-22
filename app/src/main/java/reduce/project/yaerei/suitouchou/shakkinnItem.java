package reduce.project.yaerei.suitouchou;

import android.graphics.ColorSpace;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by yaerei on 2018/02/22.
 */

@Table(name = "shakkinnItem")
public class shakkinnItem extends Model {
    @Column(name = "shakkinnname")
    public String shakkinnname;

    public shakkinnItem(){
        super();
    }
}
