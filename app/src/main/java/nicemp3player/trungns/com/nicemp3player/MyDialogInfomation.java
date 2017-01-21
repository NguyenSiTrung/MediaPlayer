package nicemp3player.trungns.com.nicemp3player;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.TextView;

/**
 * Created by Administrator on 02/08/2016.
 */
public class MyDialogInfomation extends Dialog {
    private TextView mTxtName, mTxtCasi, mTxtAlbum, mTxtTime;
    public MyDialogInfomation(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_information);
        initView();
        setCanceledOnTouchOutside(true);
        setCancelable(true);
    }

    private void initView() {
        mTxtTime = (TextView)findViewById(R.id.tv_thoiLuong_dialog);
        mTxtAlbum = (TextView)findViewById(R.id.tv_album_dialog);
        mTxtCasi =(TextView)findViewById(R.id.tv_caSi_dialog);
        mTxtName =(TextView)findViewById(R.id.tv_nameSong_dialog);
        this.getWindow().getAttributes().windowAnimations = R.style.DialogAnim;
    }

    public TextView getmTxtName() {
        return mTxtName;
    }

    public void setmTxtName(TextView mTxtName) {
        this.mTxtName = mTxtName;
    }

    public TextView getmTxtCasi() {
        return mTxtCasi;
    }

    public void setmTxtCasi(TextView mTxtCasi) {
        this.mTxtCasi = mTxtCasi;
    }

    public TextView getmTxtAlbum() {
        return mTxtAlbum;
    }

    public void setmTxtAlbum(TextView mTxtAlbum) {
        this.mTxtAlbum = mTxtAlbum;
    }

    public TextView getmTxtTime() {
        return mTxtTime;
    }

    public void setmTxtTime(TextView mTxtTime) {
        this.mTxtTime = mTxtTime;
    }
}
