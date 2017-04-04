package com.reige.dialogdemo;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn7).setOnClickListener(this);
        findViewById(R.id.btn8).setOnClickListener(this);
        findViewById(R.id.btn9).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                showNormalDialog();
                break;
            case R.id.btn2:
                showListDialog();
                break;
            case R.id.btn3:
                showSingleChoiceDialog();
                break;

            case R.id.btn4:
                showMultiChoiceDialog();

                break;
            case R.id.btn5:
                showWaitingDialog();
                break;
            case R.id.btn6:
                showProgressDialog();
                break;
            case R.id.btn7:
                showEditDialog();
                break;
            case R.id.btn8:
                showConsumerDialog();
                break;
            case R.id.btn9:
                showConsumerDialogCircle();
                break;
        }
    }

    /**
     * 用户自定义的Dialog
     */
    private void showConsumerDialogCircle() {
        final Dialog dialog = new Dialog(MainActivity.this, R.style.BottomDialog);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_circle, null);

        view.findViewById(R.id.takePhoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        view.findViewById(R.id.selectPhoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view
                .getLayoutParams();
        layoutParams.bottomMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8,
                getResources().getDisplayMetrics());
        layoutParams.width = getResources().getDisplayMetrics().widthPixels - (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16,
                        getResources().getDisplayMetrics());
        view.setLayoutParams(layoutParams);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.show();
    }

    /**
     * 用户自定义的Dialog
     */
    private void showConsumerDialog() {
        Dialog dialog = new Dialog(MainActivity.this, R.style.BottomDialog);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog, null);
        dialog.setContentView(view);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        view.setLayoutParams(layoutParams);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    /**
     * 编辑框Dialog
     */
    private void showEditDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final EditText view = new EditText(MainActivity.this);
        builder.setTitle("输入dialog")
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String content = view.getText().toString();
                    }
                })
                .show();
    }

    /**
     * 进度条Dialog
     */
    private void showProgressDialog() {
        final int MAX_PROGRESS = 100;
        final ProgressDialog progressDialog =
                new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setProgress(0);
        progressDialog.setTitle("进度条Dialog");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(MAX_PROGRESS);
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int progress = 0;
                while (progress < MAX_PROGRESS) {
                    try {
                        Thread.sleep(10);
                        progress++;
                        //子线程可以更新progressDialog进度
                        progressDialog.setProgress(progress);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                progressDialog.cancel();
            }
        }).start();
    }

    /**
     * 等待Dialog
     */
    private void showWaitingDialog() {
       /* 等待Dialog具有屏蔽其他控件的交互能力
        * setCancelable 为使屏幕不可点击，设置为不可取消(false)
        * 下载等事件完成后，主动调用函数关闭该Dialog
        * 进度条是否明确 setIndeterminate(boolean b);
        * 不明确就是滚动条的当前值自动在最小到最大值之间来回移动，形成这样一个动画效果，
        * 这个只是告诉别人“我正在工作”，但不能提示工作进度到哪个阶段。
        * 主要是在进行一些无法确定操作时间的任务时作为提示。
        * 而“明确”就是根据你的进度可以设置现在的进度值。
        */
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("等待Dialog");
        progressDialog.setMessage("等待中...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        new Thread() {
            @Override
            public void run() {
                super.run();
                SystemClock.sleep(3000);
                progressDialog.dismiss();
            }
        }.start();
    }

    /**
     * 多选Dialog
     */
    final ArrayList<Integer> choices = new ArrayList<>();

    private void showMultiChoiceDialog() {

        final String[] items = {"按钮1", "按钮2", "按钮3", "按钮4"};
        // 设置默认选中的选项
        final boolean initChoiceSets[] = {false, false, false, false};
        //choices.clear();
        AlertDialog.Builder builder =
                new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("多选Dialog")
                .setMultiChoiceItems(items, initChoiceSets, new DialogInterface
                        .OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            choices.add(which);
                        } else {
                            //有问题：是把whichButton作为index还是Object？实际上会抛下标溢出异常
                            //MultiChoiceID.remove(whichButton);
                            //需强制转换为Object类型，才会以对象的形式删除；否则会作为下标处理
                            choices.remove((Integer) which);
                        }
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }

    /**
     * 单选按钮
     */
    private void showSingleChoiceDialog() {
        final String[] items = {"按钮1", "按钮2", "按钮3", "按钮4"};
        final AlertDialog.Builder builder =
                new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("单选Dialog")
                .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }


    /**
     * 列表Dialog
     */
    private void showListDialog() {
        final String[] items = {"按钮1", "按钮2", "按钮3", "按钮4"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("列表Dialog")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }

    /**
     * 普通的Dialog
     */
    private void showNormalDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setIcon(R.mipmap.ic_launcher)
                .setTitle("普通Dialog")
                .setMessage("这是一个普通的Dialog")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
        //添加中间按钮 就有三个按钮
        //normalDialog.setNeutralButton
    }
}
