package com.tuliu.translate.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tuliu.translate.TranslateApplication;
import com.tuliu.translate.common.HashCode;
import com.tuliu.translate.common.ResultCode;
import com.tuliu.translate.entity.tuliuTranslate;
import com.tuliu.translate.entity.tuliuTranslateExample;
import com.tuliu.translate.entity.tuliuTranslateExtendExample;
import com.tuliu.translate.mapper.tuliuTranslateExtendMapper;
import com.tuliu.translate.mapper.tuliuTranslateMapper;
import com.tuliu.translate.vo.Response;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

@Service
public class UserService {
  @Resource
  private tuliuTranslateMapper translateMapper;

  @Resource
  private tuliuTranslateExtendMapper translateExtendMapper;

  public UserService() {
  }

  /*
   * 获取指定路径下的文件
   * @params path 路径
   * return List<File>
   * */
  public List<File> getFile(String path) throws NullPointerException {
    File file = new File(path);
    File[] array = file.listFiles();
    List<File> files = new ArrayList<>();
    if (array.length == 0) {
      return files;
    }
    for (File value : array) {
      if (value.isFile()) {
        files.add(value);
      }
    }
    return files;
  }

  /*
   * 清空generator生成的Mapper.xml
   * @params null
   * return boolean
   * */
  public boolean refreshFile() {
    // TODO 打成jar包路径是否变化
    String path = "src/main/resources/mapper";
    boolean result = true;
    List<File> files = getFile(path);
    if (files.size() == 0) {
      result = false;
      this.generator();
      files = this.getFile(path);
    }
    String reg = ".*(Extend).*";
    for (File file : files) {
      if (!Pattern.matches(reg, file.getName())) {
        File mapper = new File(path, file.getName());
        if (mapper.exists()) {
          result = file.delete();
        }
      }
    }
    return result;
  }

  /*
   * 生成Mapper及实体类
   * */
  public void generator() {
    try {
      List<String> warnings = new ArrayList<>();
      File configFile = new File("src/main/resources/generatorConfig.xml");
      ConfigurationParser cp = new ConfigurationParser(warnings);
      Configuration config = cp.parseConfiguration(configFile);
      DefaultShellCallback callback = new DefaultShellCallback(true);
      MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
      myBatisGenerator.generate(null);
    } catch (IOException | InvalidConfigurationException | SQLException | InterruptedException | XMLParserException e) {
      e.printStackTrace();
    }
  }

  public List<Method> getMethods(Object obj) throws IntrospectionException {
    Class<?> c = obj.getClass();//获得实体类名
    Field[] fields = obj.getClass().getDeclaredFields();//获得属性
    List<Method> methods = new ArrayList<>();
    //获得Object对象中的所有方法
    for (Field field : fields) {
      PropertyDescriptor pd = new PropertyDescriptor(field.getName(), c);
      Method method = pd.getWriteMethod();//获得set方法
      methods.add(method);
    }
    return methods;
  }

  private void setTranslate(String name, String key, String value, String lang, tuliuTranslate translate) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
    translate.setHashKey(key);
    translate.setName(name);
    List<Method> methodList = this.getMethods(translate);
    methodList = methodList.subList(3, methodList.size());
    List<String> colList = this.getColumn();
    colList = colList.subList(3, colList.size());
    for (int i = 0; i < methodList.size(); i++) {
      if (Objects.equals(lang, colList.get(i))) {
        methodList.get(i).invoke(translate, value);
      }
    }
  }

  public List<String> getColumn() {
    return this.translateExtendMapper.getColumn();
  }

  public Response<tuliuTranslate> load(String json, String lang, String name) throws IOException, IntrospectionException, InvocationTargetException, IllegalAccessException {
    JSONObject jsonObj = JSON.parseObject(URLDecoder.decode(json, "utf-8"));
    tuliuTranslate translate = new tuliuTranslate();
    String hash_key;
    String value;
    for (Map.Entry<String, Object> entry : jsonObj.entrySet()) {
      tuliuTranslateExample translateExample = new tuliuTranslateExample();
      tuliuTranslateExample.Criteria criteria = translateExample.createCriteria();
      hash_key = entry.getKey();
      value = entry.getValue().toString();
      setTranslate(name, hash_key, value, lang, translate);
      criteria.andHashKeyEqualTo(translate.getHashKey());
      criteria.andNameEqualTo(translate.getName());
      List<tuliuTranslate> translateList = this.translateMapper.selectByExample(translateExample);
      if (!translateList.isEmpty()) {
        this.translateMapper.updateByExampleSelective(translate, translateExample);
      } else
        this.translateMapper.insert(translate);
    }
    return new Response<>(ResultCode.SUCCESS_HAS_MESSAGE, "加载配置成功！");
  }

  public Response<List<String>> getDocument() {
    return new Response<>(this.translateExtendMapper.getDocument());
  }

  public Response<tuliuTranslate> update(String name, String key, String value, String lang) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
    tuliuTranslate translate = new tuliuTranslate();
    tuliuTranslateExample translateExample = new tuliuTranslateExample();
    tuliuTranslateExample.Criteria criteria = translateExample.createCriteria();
    setTranslate(name, key, value, lang, translate);
    criteria.andNameEqualTo(translate.getName());
    criteria.andHashKeyEqualTo(translate.getHashKey());
    this.translateMapper.updateByExampleSelective(translate, translateExample);
    return new Response<>(ResultCode.SUCCESS_HAS_MESSAGE, "更新成功！");
  }

  public Response<tuliuTranslate> getList(String name, String words, int offset, int limit) {
    tuliuTranslateExtendExample translateExample = new tuliuTranslateExtendExample();
    if (name != null && !Objects.equals(name, ""))
      translateExample.setName(name);
    translateExample.setOrderByClause("id asc");
    translateExample.setLimit(limit);
    translateExample.setOffset((offset - 1) * 10);
    if (!Objects.equals(words, "") && words != null) {
      long count;
      List<tuliuTranslate> result;
      List<String> cols = this.getColumn().subList(3, this.getColumn().size());
      if (name != null && !Objects.equals(name, "")) {
        count = this.translateExtendMapper.countByMultiple(name, words);
        result = this.translateExtendMapper.selectByMultiple(name, words, cols, (offset - 1) * 10, limit);
      } else {
        count = this.translateExtendMapper.countByMultiple(null, words);
        result = this.translateExtendMapper.selectByMultiple(null, words, cols, (offset - 1) * 10, limit);
      }
      return new Response<>(count, result);
    } else {
      long count = this.translateExtendMapper.countByExample(null, translateExample);
      return new Response<>(count, this.translateExtendMapper.selectByExample(translateExample));
    }
  }

  public Response<tuliuTranslate> add(String name, String lang, String words, String translateWords) throws NoSuchAlgorithmException, IntrospectionException, InvocationTargetException, IllegalAccessException {
    String hash_key = new HashCode().hash(words);
    tuliuTranslate translate = new tuliuTranslate();
    tuliuTranslateExample translateExample = new tuliuTranslateExample();
    tuliuTranslateExample.Criteria criteria = translateExample.createCriteria();
    translate.setZh(words);
    setTranslate(name, hash_key, translateWords, lang, translate);
    criteria.andHashKeyEqualTo(translate.getHashKey());
    criteria.andNameEqualTo(translate.getName());
    if (!this.translateMapper.selectByExample(translateExample).isEmpty()) {
      return new Response<>(ResultCode.FAIL, "添加失败，KEY重复");
    }
    this.translateMapper.insert(translate);
    return new Response<>(ResultCode.SUCCESS_HAS_MESSAGE, "添加成功！");
  }

  public Response<tuliuTranslate> appendColumn(String col) {
    if (this.refreshFile()) {
      this.translateExtendMapper.appendTableColumn(col);
      this.generator();
      TranslateApplication.restart();
      return new Response<>(ResultCode.SUCCESS_HAS_MESSAGE, "添加字段成功！");
    } else {
      return new Response<>(ResultCode.SUCCESS_HAS_MESSAGE, "添加字段失败！");
    }
  }

  public List<tuliuTranslate> getTable(String name) {
    tuliuTranslateExample translateExample = new tuliuTranslateExample();
    tuliuTranslateExample.Criteria criteria = translateExample.createCriteria();
    criteria.andNameEqualTo(name);
    return this.translateMapper.selectByExample(translateExample);
  }

  /*
   * TODO
   *  1.导出js 已完成
   *  2.导入Excel
   *  3.删除列
   * */
}
