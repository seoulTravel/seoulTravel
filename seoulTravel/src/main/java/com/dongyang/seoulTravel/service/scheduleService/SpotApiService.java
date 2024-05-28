package com.dongyang.seoulTravel.service.scheduleService;

import com.dongyang.seoulTravel.dto.schedule.SpotDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

@Service
public class SpotApiService {
    private final RestTemplate restTemplate;

    public SpotApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<SpotDto> getSpotApiService() throws Exception {
        String url = "http://openapi.seoul.go.kr:8088/4a75626b4e736838383947556e7664/xml/TbVwAttractions/1/5/";
        String response = restTemplate.getForObject(url, String.class);

        // XML to List<SpotDto> conversion
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new ByteArrayInputStream(response.getBytes()));

        NodeList nodeList = document.getElementsByTagName("row");
        List<SpotDto> spots = new ArrayList<>();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            SpotDto spotDto = new SpotDto();

            spotDto.setPostSn(getNodeValue(element, "POST_SN"));
            spotDto.setLangCodeId(getNodeValue(element, "LANG_CODE_ID"));
            spotDto.setPostSj(getNodeValue(element, "POST_SJ"));
            spotDto.setPostUrl(getNodeValue(element, "POST_URL"));
            spotDto.setAddress(getNodeValue(element, "ADDRESS"));
            spotDto.setNewAddress(getNodeValue(element, "NEW_ADDRESS"));
            spotDto.setCmnnTelno(getNodeValue(element, "CMMN_TELNO"));
            spotDto.setSubwayInfo(getNodeValue(element, "SUBWAY_INFO"));
            spotDto.setTag(getNodeValue(element, "TAG"));

            spots.add(spotDto);
        }

        return spots;
    }

    private String getNodeValue(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList != null && nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return null;
    }
}
