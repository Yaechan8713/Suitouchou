package reduce.project.yaerei.suitouchou;

import android.graphics.ColorSpace;
import android.graphics.PorterDuff;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by yaerei on 2018/02/27.
 */

@Table(name = "listtitleItem")
public class listtitleItem extends Model {
    @Column(name = "listtitlename")
    public String listtitlename;

    public listtitleItem(){
        super();
    }
}
