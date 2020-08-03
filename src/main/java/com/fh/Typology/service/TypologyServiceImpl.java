package com.fh.Typology.service;


import com.fh.Typology.mapper.TypologyMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TypologyServiceImpl implements TypologyService {

    @Resource
    private TypologyMapper typologyMapper;

    public List<Map<String,Object>> queryTypology(){
        List<Map<String,Object>> allList = typologyMapper.queryTypology();
        List<Map<String,Object>> plist = new ArrayList<Map<String,Object>>();
        for(Map map:allList){
            if (map.get("pid").equals(0)){
                plist.add(map);
            }
        }
        selectChildren(allList,plist);
        return plist;
    }
    public void selectChildren(List<Map<String,Object>> allList, List<Map<String,Object>> plist){

        for(Map<String,Object> pmap:plist){
            List<Map<String,Object>> childrenList = new ArrayList<Map<String,Object>>();
            for (Map<String,Object> amap:allList) {

                if(pmap.get("id").equals(amap.get("pid"))){
                    childrenList.add(amap);
                }
            }
            if (childrenList!=null && childrenList.size()>0){
                pmap.put("children",childrenList);
                selectChildren(allList,childrenList);
            }
        }
    }


}
