package com.example.simplebackup;

import androidx.lifecycle.ViewModel;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyViewModel extends ViewModel {
    String TAG = "TAG";
    public class Item{
        String ssidname;
        String pskmode;
        String password;

        private String getSsidname() {
            return ssidname;
        }


        private String getPskmode(){
            return pskmode;
        }

        private String getPassword(){
            return password;
        }

        private void setSsidname(String src) {
            this.ssidname = src;
        }


        private void setPassword(String src) {
            this.password = src;
        }


        private void setPskmode(String src) {
            this.pskmode = src;
        }


    }
    private static ArrayList<Item> ssidItem = new ArrayList<>() ;

    public String Read(String command) throws Exception{
        Process process = null;
        DataOutputStream dataOutputStream = null;
        DataInputStream dataInputStream = null;
        StringBuffer buf = new StringBuffer();
        try {
            process = Runtime.getRuntime().exec("su");
            dataOutputStream = new DataOutputStream(process.getOutputStream());
            dataInputStream = new DataInputStream(process.getInputStream());
            dataOutputStream.writeBytes(command);
            dataOutputStream.writeBytes("exit\n");
            dataOutputStream.flush();

            InputStreamReader inputStreamReader = new InputStreamReader(dataInputStream,"utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                buf.append(line);
            }
            bufferedReader.close();
            inputStreamReader.close();
            process.waitFor();
        }catch(Exception e){
            throw e;
        }finally {
            try{
                if(dataOutputStream != null){
                    dataOutputStream.close();
                }
                if(dataInputStream != null){
                    dataInputStream.close();
                }
                process.destroy();
            }catch(Exception e){
                throw e;
            }
        }

        return buf.toString();
    }

    public ArrayList<String> SplitStr(String src, String rgex,int index){
        ArrayList<String> list = new ArrayList<String>();
        Pattern r = Pattern.compile(rgex);
        Matcher m = r.matcher(src);

        int i = 0;
        while (m.find()){
            list.add(m.group(index));//提取第index括号的数据
            //Log.d(TAG, String.format("SplitStr:%d %s", i, list.get(i)));
            i++;
        }


        return list;
    }


    public String displayStr(ArrayList<String> srcList,String tab){
        if(srcList == null){
            return null;
        }
        String desStr = "";
        for(int index = 0;index < srcList.size();index++){
            desStr = desStr + srcList.get(index) + tab;
        }
        return desStr;
    }

    public void  praserStr(){

        try {
            String  rgex = "<WifiConfiguration>(.*?)</WifiConfiguration>";
            String str1 = Read("cat /data/misc/wifi/WifiConfigStore.xml\n");
            ArrayList<String> strList = SplitStr(str1,rgex,1);//解析出每一个Network里面包含一个ssid所有的数据
            rgex = "<string name=\"ConfigKey\">&quot;(.+?)&quot;(.+?)</string>";//ssid 和 加密方式的正则提取公式
            String rgex1 = "<string name=\"PreSharedKey\">&quot;(.+?)&quot;</string>";//wifi密码提取的正则公式

            for(int index = 0;index <= strList.size();index ++){
                Item mitem = new  Item();
                mitem.setSsidname(displayStr(SplitStr(strList.get(index),rgex,1),""));//ssidname = displayStr(SplitStr(strList.get(index),rgex,1),"");//提取ssid
                mitem.setPskmode(displayStr(SplitStr(strList.get(index),rgex,2),""));//pskmode = displayStr(SplitStr(strList.get(index),rgex,2),"");//提取加密方式
                mitem.setPassword(displayStr(SplitStr(strList.get(index),rgex1,1),""));//password = displayStr(SplitStr(strList.get(index),rgex1,1),"");//提取wifi密码
                ssidItem.add(mitem);

            }
            //Log.d(TAG, String.format("praserStr: %s",strList.size()));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

   public ArrayList<Item> getSsidItem(){

        if(!ssidItem.isEmpty()){
            ssidItem.clear();
        }
       praserStr();
/*        for(int index = 0;index <ssidItem.size();index++){
            Log.d(TAG,String.format("ssiditem：%s index : %s",ssidItem.get(index).ssidname,index));
            Log.d(TAG,String.format("ssiditem：%s",ssidItem.get(index).pskmode));
            Log.d(TAG,String.format("ssiditem：%s",ssidItem.get(index).password));
        }*/

        return ssidItem;
    }

}
