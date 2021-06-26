package oleksandr.kotyuk.orthodoxcalendarfree;

import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

class MenuActivity extends AppCompatActivity {
protected boolean flag_bookmarks_on;
protected MenuItem menu_item1,menu_item2,menu_item3,menu_item4;
    protected void workWithFavouriteItem(Menu menu) {
        if(flag_bookmarks_on) {
            menu_item4.setIcon(R.drawable.ic_action_bookmarks_on);
            menu_item4.setTitle(getString(R.string.menu_item4_delete_prayers));
        }
        else {
            menu_item4.setIcon(R.drawable.ic_action_bookmarks_off);
            menu_item4.setTitle(getString(R.string.menu_item4_prayers));
        }
    }
}
