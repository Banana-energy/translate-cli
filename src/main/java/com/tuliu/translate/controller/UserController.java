package com.tuliu.translate.controller;

import com.tuliu.translate.entity.tuliuTranslate;
import com.tuliu.translate.service.UserService;
import com.tuliu.translate.vo.Response;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = {"/user"})
public class UserController {

  @Resource
  private UserService userService;

  public List<Method> getMethod(Object obj) throws IntrospectionException {
    Class<?> c = obj.getClass();//获得实体类名
    Field[] fields = obj.getClass().getDeclaredFields();//获得属性
    List<Method> methods = new ArrayList<>();
    //获得Object对象中的所有方法
    for (Field field : fields) {
      PropertyDescriptor pd = new PropertyDescriptor(field.getName(), c);
      Method method = pd.getReadMethod();//获得get方法
      methods.add(method);
      //getMethod.invoke(obj);//此处为执行该Object对象的get方法
//      Method setMethod = pd.getWriteMethod();//获得set方法
      //setMethod.invoke(obj,"参数");//此处为执行该Object对象的set方法
    }
    return methods;
  }

  @GetMapping({"/getDocument"})
  public Response<List<String>> getDocument() {
    return this.userService.getDocument();
  }

  @PostMapping({"/load"})
  public Response<tuliuTranslate> load(@RequestParam(value = "json", defaultValue = "{}") String json, String lang, String name) throws IOException, IntrospectionException, InvocationTargetException, IllegalAccessException {
    return this.userService.load(json, lang, name);
  }

  @GetMapping({"/update"})
  public Response<tuliuTranslate> update(String name, String key, String value, String lang) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
    return this.userService.update(name, key, value, lang);
  }

  @GetMapping({"/getList"})
  public Response<tuliuTranslate> init(
      String name,
      String words,
      @RequestParam(value = "page", required = false, defaultValue = "1") int offset,
      @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
    return this.userService.getList(name, words, offset, limit);
  }

  @GetMapping({"/add"})
  public Response<tuliuTranslate> add(String name, String lang, String words, String translateWords) throws NoSuchAlgorithmException, IntrospectionException, InvocationTargetException, IllegalAccessException {
    return this.userService.add(name, lang, words, translateWords);
  }

  @GetMapping({"append"})
  public Response<tuliuTranslate> append(String col) {
    return this.userService.appendColumn(col);
  }

  @GetMapping({"getColumn"})
  public Response<List<String>> getColumn() {
    List<String> cols = this.userService.getColumn();
    return new Response<>(cols);
  }

  @GetMapping({"export"})
  public ByteArrayResource export(String name) {
    byte[] result = null;
    ByteArrayOutputStream byteArrayOutputStream = null;
    WritableWorkbook writableWorkbook = null;
    try {
      byteArrayOutputStream = new ByteArrayOutputStream();
      writableWorkbook = Workbook.createWorkbook(byteArrayOutputStream);
      WritableSheet writableSheet = writableWorkbook.createSheet("翻译列表", 0);
      List<String> allCol = this.userService.getColumn();
      for (int i = 0; i < allCol.size(); i++) {
        writableSheet.addCell(new Label(i, 0, allCol.get(i)));
      }
      List<tuliuTranslate> itemList = this.userService.getTable(name);
      int i = 1;
      for (tuliuTranslate item : itemList) {
        List<Method> methods = this.getMethod(item);
        int c = 0;
        for (Method method : methods) {
          writableSheet.addCell(new Label(c, i, String.valueOf(method.invoke(item))));
          c++;
        }
        i++;
      }
      writableWorkbook.write();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (writableWorkbook != null) {
          writableWorkbook.close();
        }
        if (byteArrayOutputStream != null) {
          result = byteArrayOutputStream.toByteArray();
          byteArrayOutputStream.close();
        }
      } catch (IOException | jxl.write.WriteException e) {
        e.printStackTrace();
      }
    }
    if (result == null) {
      result = new byte[0];
    }
    return new ByteArrayResource(result);
  }

  @GetMapping({"/exportJS"})
  public Response<List<tuliuTranslate>> exportJS(String name) {
    List<tuliuTranslate> table = this.userService.getTable(name);
//    List<JSONObject> list = new ArrayList<>();
//    for (tuliuTranslate row : table) {
//      String[] keys = row.getHashKey().split("\\.");
//      JSONObject temp = new JSONObject();
//      for (int i = keys.length - 1; i >= 0; i--) {
//        if (i == keys.length - 1) {
//          temp.put(keys[i], row.getZh());
//        } else {
//          temp.put(keys[i], temp);
//          temp.remove(keys[i + 1]);
//        }
//      }
//      list.add(temp);
//    }
    return new Response<>(table);
  }

}
