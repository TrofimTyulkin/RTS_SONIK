package org.example.Generator;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.util.ArrayList;

public class Data implements ActionListener {
    ArrayList<Double> dataVoltagePanel = new ArrayList<>();//выходное напряжение панели
    ArrayList<Double> dataAmperagePanel = new ArrayList<>();//выходная сила тока панели
    ArrayList<Double> dataVoltageController = new ArrayList<>();//выходное напряжение контроллера
    ArrayList<Double> dataAmperageController = new ArrayList<>();//выходная сила тока контроллера
    ArrayList<Double> dataCapacityBattery = new ArrayList<>();//текущая емкость батареи
    ArrayList<Double> dataProduce = new ArrayList<>();//текущее потребление энергии
    ArrayList<LocalTime> dataDate = new ArrayList<>();//дата
    ArrayList<Boolean> dataIsCharge = new ArrayList<>();//заряжается ли батарея
    ArrayList<Boolean> dataIsLowCapacity = new ArrayList<>();//низкий заряд
    ArrayList<Boolean> dataIsOverCharge = new ArrayList<>();//перезаряд(заряд больше 48)
    ArrayList<Double> dataPower = new ArrayList<>();//общая мощность панели
    ArrayList<Double> dataMoney = new ArrayList<>();//полученные деньги
    ArrayList<Double> dataErrorMoneyBattery = new ArrayList<>();//полученные деньги
    ArrayList<Double> dataErrorMoneyPanel = new ArrayList<>();//полученные деньги
    ArrayList<Double> dataTotalMoney = new ArrayList<>();//полученные деньги
    boolean isErrorRecharge = false;//ошибка перезарядки (зарядка не отключается при достижении 48 ватт)
    double curCapacity;//текущая емкость батареи
    boolean isErrorVoltage = false;//ошибка выходного напряжения контроллера
    Timer timer;
    double maxCapacity = 48.0;//максимальная емкость
    int minProduce = 10;//минимальное потребление
    int maxProduce = 20;//максимальное потребление
    int absMaxProduce = 100;//максимально возможное потребление

    public Data() {
        dataCapacityBattery.add(30.0);//задаем начальную емкость
        dataTotalMoney.add(0.0);//задаем начальную емкость
        timer = new Timer(500, this);//раз в полсекунды обновляем показания датчиков
        timer.start();
        Timer timer1 = new Timer(7000, e -> {
            if (Generator.generate(0,1,3) > 0.7){
                if (curCapacity > 47) isErrorRecharge = true;
                if (Generator.generate(0,1,3) > 0.8){
                    isErrorVoltage = true;
                }
            }else{
                if (Generator.generate(0,1,3) > 0.7){
                    isErrorVoltage = true;
                    if (Generator.generate(0,1,3) > 0.8){
                        if (curCapacity > 47) isErrorRecharge = true;
                    }
                }
            }

            System.out.println(isErrorVoltage +" " + isErrorRecharge);
        });
        timer1.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {//вот тут обновляем показания датчиков
        dataDate.add(LocalTime.now());
        double curVolt = Generator.generate(5, 15, 3);
        double curAmperage = Generator.generate(2, 7, 3);
        double curProduce = Generator.generate(minProduce, maxProduce, 3);
        double curVoltageController = 12;
        double curAmperageController = Generator.round(curVolt * curAmperage / curVoltageController);
        double prevCapacity = getLastDataCapacityBattery();
        double prevTotalMoney = getLastDataTotalMoney();
        double curMoney = 0.0;


        curCapacity = Generator.round(prevCapacity + curAmperage * curVolt / 60 - curProduce / 60);

        if (isErrorRecharge) {
            maxCapacity = 55;
            curMoney -= 5;
            dataErrorMoneyBattery.add(-5.0);
        } else{
            maxCapacity = 48;
            dataErrorMoneyBattery.add(0.0);
        }
        if (isErrorVoltage){
            curVoltageController = Generator.generate(10, 15, 3);
            curMoney -= 5;
            dataErrorMoneyPanel.add(-5.0);
        }else  dataErrorMoneyPanel.add(0.0);
        if(curCapacity < maxCapacity){
            if (curCapacity < 10){
                dataIsLowCapacity.add(true);
                curProduce = 0;
                curCapacity = Generator.round(prevCapacity + curAmperage*curVolt/60 - curProduce/60);
                System.err.println("LOW CAPACITY" + curProduce);
            }else{
                dataIsLowCapacity.add(false);
            }

            dataIsCharge.add(true);
            dataIsOverCharge.add(false);
        }else{
            dataIsCharge.add(false);
            curVolt = 0;
            curAmperage = 0;
            curAmperageController = 0;
            curVoltageController = 0;
            curCapacity = Generator.round(prevCapacity - curProduce/60);
            if (curCapacity>50){
                dataIsOverCharge.add(true);
                if (curProduce<0) curProduce = 0;
            }
            curCapacity = Generator.round(prevCapacity - curProduce/60);
        }

        double bid = 2;

        //if (curProduce > 60) bid = 3;
        if (curProduce <= 60 && curProduce > 0) bid = 2;
        else if (curProduce <= 0) bid = 4;

        curMoney += Generator.round(curProduce*bid/60);

        dataCapacityBattery.add(curCapacity);

        dataTotalMoney.add(Generator.round(prevTotalMoney + curMoney));
        dataMoney.add(curMoney);

        dataPower.add(Generator.round(curAmperageController*curVoltageController));
        dataVoltagePanel.add(curVolt);
        dataAmperagePanel.add(curAmperage);
        dataAmperageController.add(curAmperageController);
        dataVoltageController.add(curVoltageController);
        dataProduce.add(curProduce);
        System.err.println(bid);
        System.out.printf("CapacityBattery = %s ", getLastDataCapacityBattery());
        System.out.printf("prevTotalMoney = %s ", prevTotalMoney);
        System.out.printf("curMoney = %s ", curMoney);
        System.out.printf("dataTotalMoney = %s ", Generator.round(prevTotalMoney + curMoney));
        System.out.printf("curProduce = %s ", curProduce);
        System.out.printf("curVolt = %s ", curVolt);
        System.out.printf(" curAmperage = %s ",curAmperage);
        System.out.printf(" curVoltageController = %s ",curVoltageController);
        System.out.printf(" curAmperageController = %s \n",curAmperageController);
        //System.out.println(data.get(data.size()-1));
    }
    public Double getLastDataCapacityBattery(){
        return dataCapacityBattery.get(dataCapacityBattery.size()-1);
    }
    public Double getLastDataMoneyErrorPanel(){
        return dataErrorMoneyPanel.get(dataErrorMoneyPanel.size()-1);
    }
    public Double getLastDataMoneyErrorBattery(){
        return dataErrorMoneyBattery.get(dataErrorMoneyBattery.size()-1);
    }
    public Double getLastDataTotalMoney(){
        return dataTotalMoney.get(dataTotalMoney.size()-1);
    }
    public ArrayList<Double> getDataVoltagePanel() {
        return dataVoltagePanel;
    }
    public ArrayList<Double> getDataAmperagePanel() {
        return dataAmperagePanel;
    }

    public ArrayList<LocalTime> getDataDate() {
        return dataDate;
    }
    public Double getLastDataVoltController() {
        return dataVoltageController.get(dataVoltageController.size()-1);
    }

    public Double getLastAmperageController() {
        return dataAmperageController.get(dataAmperageController.size()-1);
    }
    public Double getLastDataVoltagePanel() {
        return dataVoltagePanel.get(dataVoltagePanel.size()-1);
    }
    public Double getLastDataAmperagePanel(){
        return dataAmperagePanel.get(dataAmperagePanel.size()-1);
    }
    public Boolean getLastDataIsCharge(){
        return dataIsCharge.get(dataIsCharge.size()-1);
    }

    public Boolean getLastDataIsLowCapacity(){
        return dataIsLowCapacity.get(dataIsLowCapacity.size()-1);
    }
    public Double getLastDataProduce(){
        return dataProduce.get(dataProduce.size()-1);
    }
    public Boolean getLastDataIsOverCharge(){
        return dataIsOverCharge.get(dataIsOverCharge.size()-1);
    }
    public ArrayList<Double> getDataVoltageController() {
        return dataVoltageController;
    }

    public ArrayList<Double> getDataAmperageController() {
        return dataAmperageController;
    }

    public ArrayList<Double> getDataCapacityBattery() {
        return dataCapacityBattery;
    }
    public ArrayList<Double> getDataCapacityBatteryForChart() {
        ArrayList<Double> res = new ArrayList<>();
        int maxSize = dataCapacityBattery.size();
        if (maxSize >=24)  maxSize = 24;
        for (int i = dataCapacityBattery.size() - 1; i >= dataCapacityBattery.size() - maxSize; i--) {
            res.add(dataCapacityBattery.get(i));
        }
        return res;
    }
    public ArrayList<Double> getDataPowerForChart() {
        ArrayList<Double> res = new ArrayList<>();
        int maxSize = dataPower.size();
        if (maxSize >=24)  maxSize = 24;
        for (int i = dataPower.size() - 1; i >= dataPower.size() - maxSize; i--) {
            res.add(dataPower.get(i));
        }
        return res;
    }
    public ArrayList<Double> getDataProduceForChart() {
        ArrayList<Double> res = new ArrayList<>();
        int maxSize = dataProduce.size();
        if (maxSize >=24)  maxSize = 24;
        for (int i = dataProduce.size() - 1; i >= dataProduce.size() - maxSize; i--) {
            res.add(dataProduce.get(i));
        }
        return res;
    }

    public void setProduce(int min, int max){
        minProduce = min;
        maxProduce = max;
    }
    public void setProduce(int percent){
        maxProduce = absMaxProduce*percent/100;
        minProduce = maxProduce - 10;
    }
    public void addProduce(int delta){
        minProduce+=delta;
        maxProduce+=delta;
    }
    public boolean isErrorRecharge() {
        return isErrorRecharge;
    }

    public void setErrorRecharge(boolean errorRecharge) {
        isErrorRecharge = errorRecharge;
    }

    public boolean isErrorVoltage() {
        return isErrorVoltage;
    }

    public void setErrorVoltage(boolean errorVoltage) {
        isErrorVoltage = errorVoltage;
    }
    public Timer getTimer(){
        return timer;
    }
}
