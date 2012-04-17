package com.fantaros.android.autocommand;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AutoCommanderApp extends Activity {
	protected static final String _version_ = "0.1";
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button imgButton = (Button)findViewById(R.id.button1);
		imgButton.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				finish();
			}
			
		});
		ShellLoader loader = new ShellLoader();
		loader.readShellFiles();
		String commands = loader.readAllShells();
		int ret = -2;
		if (commands != null && !commands.equals("")) {
			if (AndroidCommand.isRooted()) {
				ret = AndroidCommand.execRooted(commands);
			} else {
				ret = AndroidCommand.exec(commands);
			}
		}
		TextView large = (TextView)findViewById(R.id.textView1);
		switch (ret) {
		case -2:
			Toast.makeText(this, "文件夹内没有包含shell脚本文件!", Toast.LENGTH_SHORT).show();
			large.setText("文件夹内没有包含shell脚本文件!");
			break;
		case 0:
			Toast.makeText(this, "所有脚本文件执行成功", Toast.LENGTH_SHORT).show();
			this.finish();
//			large.setText("所有脚本文件执行成功!");
			break;
		case -1:
		default:
			Toast.makeText(this, "脚本文件执行时遇到了问题!", Toast.LENGTH_SHORT).show();
			large.setText("脚本文件执行时遇到了问题!");
			break;
		}
	}
}