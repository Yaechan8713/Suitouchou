package reduce.project.yaerei.suitouchou;

import android.graphics.ColorSpace;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by yaerei on 2018/02/25.
 */

@Table(name = "keishouItem")
public class keishouItem extends Model {
    @Column(name = "keishouname")
    public String keishouname;

    public keishouItem(){
        super();
    }
}
