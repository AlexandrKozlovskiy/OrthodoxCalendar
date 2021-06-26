package oleksandr.kotyuk.orthodoxcalendarfree.models;

public class DrawerItem {

	String ItemName;
	int imgResID;
	String title;

	public DrawerItem(String ItemName, int imgResID) {
this.ItemName = ItemName;
		this.imgResID = imgResID;
	}

	public DrawerItem(String title) {
		this(null, 0);
		this.title = title;
	}

	public String getItemName() {
		return ItemName;
	}

	public void setItemName(String itemName) {
		ItemName = itemName;
	}

	public int getImgResID() {
		return imgResID;
	}

	public void setImgResID(int imgResID) {
		this.imgResID = imgResID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
