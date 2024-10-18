package org.example.Generator;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Data implements ActionListener {
    List<Double> dataVoltagePanel = new ArrayList<>();//выходное напряжение панели
    List<Double> dataAmperagePanel = new ArrayList<>();//выходная сила тока панели
    List<Double> dataVoltageController = new ArrayList<>();//выходное напряжение контроллера
    List<Double> dataAmperageController = new ArrayList<>();//выходная сила тока контроллера
    List<Double> dataCapacityBattery = new ArrayList<>();//текущая емкость батареи
    List<Double> dataProduce = new ArrayList<>();//текущее потребление энергии
    List<LocalDate> dataDate = new ArrayList<>();//дата
    List<Boolean> dataIsCharge = new ArrayList<>();//заряжается ли батарея
    List<Boolean> dataIsLowCapacity = new ArrayList<>();//низкий заряд
    List<Boolean> dataIsOverCharge = new ArrayList<>();//перезаряд(заряд больше 48)
    boolean isErrorRecharge = false;//ошибка перезарядки (зарядка не отключается при достижении 48 ватт)
    double curCapacity;//текущая емкость батареи
    boolean isErrorVoltage = false;//ошибка выходного напряжения контроллера

    double maxCapacity = 48.0;//максимальная емкость
    int minProduce = 10;//минимальное потребление
    int maxProduce = 20;//максимальное потребление
    int absMaxProduce = 100;//максимально возможное потребление

    public Data() {
        dataCapacityBattery.add(30.0);//задаем начальную емкость
        Timer timer = new Timer(500, this);//раз в полсекунды обновляем показания датчиков
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
        double curVolt = Generator.generate(5,15,3);
        double curAmperage = Generator.generate(2,7,3);
        double curProduce = Generator.generate(minProduce,maxProduce,3);
        double curVoltageController = 12;
        double curAmperageController = Generator.round(curVolt*curAmperage/curVoltageController);
        double prevCapacity =  getLastDataCapacityBattery();

        curCapacity = Generator.round(prevCapacity + curAmperage*curVolt/60 - curProduce/60);

        if (isErrorRecharge) maxCapacity = 55;
        else maxCapacity = 48;
        if (isErrorVoltage) curVoltageController = Generator.generate(10,15, 3);

        if(curCapacity < maxCapacity){
            if (curCapacity < 10){
                dataIsLowCapacity.add(true);
                curProduce = 0;
                curCapacity = Generator.round(prevCapacity + curAmperage*curVolt/60 - curProduce/60);
                System.err.println("LOW CAPACITY" + curProduce);
                //if (curProduce==0)System.err.println(prevCapacity+" "+curCapacity);
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

        dataCapacityBattery.add(curCapacity);



        dataVoltagePanel.add(curVolt);
        dataAmperagePanel.add(curAmperage);
        dataAmperageController.add(curAmperageController);
        dataVoltageController.add(curVoltageController);
        dataDate.add(LocalDateTime.now().toLocalDate());
        dataProduce.add(curProduce);
        System.out.printf("CapacityBattery = %s ", getLastDataCapacityBattery());
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
    public List<Double> getDataVoltagePanel() {
        return dataVoltagePanel;
    }
    public List<Double> getDataAmperagePanel() {
        return dataAmperagePanel;
    }

    public List<LocalDate> getDataDate() {
        return dataDate;
    }

    public Double getLastDataVoltController() {return dataVoltageController.get(dataVoltageController.size()-1); }

    public Double getLastAmperageController() { return dataAmperageController.get(dataAmperageController.size()-1);}
    public Double getLastDataVoltagePanel() {return dataVoltagePanel.get(dataVoltagePanel.size()-1); }
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
    public List<Double> getDataVoltageController() {
        return dataVoltageController;
    }

    public List<Double> getDataAmperageController() {
        return dataAmperageController;
    }

    public List<Double> getDataCapacityBattery() {
        return dataCapacityBattery;
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
}
