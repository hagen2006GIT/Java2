package ru.course2.spring1;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.security.PrivilegedAction;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.function.BinaryOperator;

@Component public class MiddleComponent implements BinaryOperator <Model> {
    @Getter @Setter String logFile;
    StringBuilder loggingString=new StringBuilder();
    DateFormat formatter=new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    @Override
    public Model apply(Model model, Model model2) {
        return null;
    }
    public void init() {}
    void logging(String filename,StringBuilder loggingStr){
        if(!(filename==null)) {
            var context = new AnnotationConfigApplicationContext("ru.course2.spring1"); // прочитал контекст пакета
            Util utils = context.getBean("util", Util.class);
            try {
                utils.data2Log(filename,loggingStr.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
// проверка 4 - промежуточная компонента проверяет, что тип приложения соответствует одному из: "web", "mobile".
// Если там записано что-либо иное, то оно преобразуется к виду "other."+значение
@LogTransformation ("fix_apple_type.log")
@Component (value="FIX_APPL_TYPE") class MiddleFixApplType extends MiddleComponent implements BinaryOperator <Model> {
    @Override public Model apply(Model model, Model model2) {
        loggingString.append(this.getClass().getName()+"\n");
        loggingString.append("старт операции: ").append(formatter.format(new java.util.Date().getTime())+"\n");
        loggingString.append("модель ДО старта операции:"+model.toString()+"\n");
        for(ModelStructure mod:model2.fLine) {
            if(!mod.getAppl_type().equals("mobile") && !mod.getAppl_type().equals("web"))
                mod.setAppl_type("other."+mod.getAppl_type());
        }
        loggingString.append("модель ПОСЛЕ обработки:"+model.toString()+"\n");
        logging(this.getLogFile(),loggingString);
        return model2;
    }
}
// проверка 3 - промежуточная компонента проверки данных исправляет ФИО так, чтобы каждыё его компонент начинался с большой буквы
@LogTransformation ("fix_fio.log")
@Component (value="FIX_FIO") class MiddleFixFIO extends MiddleComponent implements BinaryOperator <Model> {
    @Override
    public Model apply(Model model, Model model2) {
        loggingString.append(this.getClass().getName()+"\n");
        loggingString.append("старт операции: ").append(formatter.format(new java.util.Date().getTime())+"\n");
        loggingString.append("модель ДО старта операции:"+model.toString()+"\n");
        for (ModelStructure mod : model2.fLine) {
            mod.setFioF(getCapsFirst(mod.getFioF()));
            mod.setFioI(getCapsFirst(mod.getFioI()));
            mod.setFioO(getCapsFirst(mod.getFioO()));
        }
        loggingString.append("модель ПОСЛЕ обработки:"+model.toString()+"\n");
        logging(this.getLogFile(),loggingString);
        return model2;
    }

    private String getCapsFirst(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
// проверка 5 - промежуточная компонента проверки даты проверяет её наличие.
// Если дата не задана, то человек не вносится в базу, а сведения о имени файла и значения человека заносятся в отдельный лог
@LogTransformation ("fix_access_date.log")
@Component (value="CHECK_ACCESS_DATE") class MiddleCheckAccessDate extends MiddleComponent implements BinaryOperator <Model> {
    @Override public Model apply(Model model, Model model2) {
        loggingString.append(this.getClass().getName()+"\n");
        loggingString.append("старт операции: ").append(formatter.format(new java.util.Date().getTime())+"\n");
        loggingString.append("модель ДО старта операции:"+model.toString()+"\n");
        var context=new AnnotationConfigApplicationContext("ru.course2.spring1"); // прочитал контекст пакета
        Util utils=context.getBean("util",Util.class);
        for(ModelStructure mod:model2.fLine) {
            if(mod.getAccess_date()==null) {
                mod.setDataOk(false); // строку на импортирую в БД (признак - false)
                try { // и сведения по соответствующей строке в файле заношу в лог
                    utils.data2Log("./src/test/importError.log",mod.gerFullString());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        loggingString.append("модель ПОСЛЕ обработки:"+model.toString()+"\n");
        logging(this.getLogFile(),loggingString);
        return model2;
    }
}

