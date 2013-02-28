/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.web;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.db.entities.Deviation;
import ru.model.DeviationFacade;

/**
 *
 * @author Vitaly_Petrov
 */
@Controller
public class DeviationController {
    
    @Autowired
    DeviationFacade df;
    
    private final String[] ruFields = new String[]{"Идентификатор записи",
        "Номер измерительного датчика", "Значение аварийного порога отклонения",
        "Активный/Неактивный"
    };
    
    private final String[] dbFields = new String[]{"id", "number", "value", "isActive"};

    @RequestMapping(value="/deviation.htm",method=RequestMethod.GET)
    public String showForm(){
        return "deviation";
    }
    
    @RequestMapping(value = "/create.htm", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")  
    public @ResponseBody String create(HttpServletRequest request, HttpServletResponse response) {
        String number = request.getParameter("number");
        String value = request.getParameter("value");
        String isActive = request.getParameter("isActive");
        
        Deviation newRow = new Deviation();
        newRow.setId(null);
        newRow.setNumber(Integer.valueOf(number));
        newRow.setValue(Double.valueOf(value));
        newRow.setIsActive(Short.valueOf(isActive));
        
        Long result = df.create(newRow);
        
        return String.valueOf(result);
    }
    
    @RequestMapping(value = "/update.htm", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")  
    public @ResponseBody void update(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        Long id = Long.valueOf(request.getParameter("id"));
        Integer number = Integer.valueOf(request.getParameter("number"));
        Double value = Double.valueOf(request.getParameter("value"));
        Short isActive = Short.valueOf(request.getParameter("isActive"));
        
        df.edit(new Deviation(id, number, value, isActive));
    }
    
    @RequestMapping(value = "/delete.htm", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")  
    public @ResponseBody String delete(HttpServletRequest
            request, HttpServletResponse response) 
            throws Exception {
        Long id = Long.valueOf(request.getParameter("id"));
        Integer number = Integer.valueOf(request.getParameter("number"));
        Double value = Double.valueOf(request.getParameter("value"));
        Short isActive = Short.valueOf(request.getParameter("isActive"));
        
        df.remove(new Deviation(id, number, value, isActive));
        return request.getParameter("id");
    }
    
    @RequestMapping(value = "/requestFields.htm", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")  
    public @ResponseBody String requestFields() throws Exception {
        Gson gson = new Gson();
        List<String[]> fieldsList = new ArrayList<String[]>();
        fieldsList.add(ruFields);
        fieldsList.add(dbFields);
        
        String result = gson.toJson(fieldsList);
        
        return result;
    }
    
    @RequestMapping(value = "/requestContent.htm", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")  
    public @ResponseBody String requestContent() throws Exception {
        
        List<Deviation> devList = df.findAll();
        Gson gson = new Gson();
        String result = gson.toJson(devList);
        
        return result;
    }
}
