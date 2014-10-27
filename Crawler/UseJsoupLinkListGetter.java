package Crawler;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class UseJsoupLinkListGetter {
	private String url;
	public UseJsoupLinkListGetter(String url){
		this.url=url;
	}
	public ArrayList<LinkDetails> getLinkList(){
		ArrayList<LinkDetails> linkList=new ArrayList<LinkDetails>();
		try{
			  int number;
			  String link;
			  Document doc=null;
			  doc = Jsoup.connect(url).timeout(10000).get();  
			  Elements elements = doc.getElementsByTag("li");				  
			  for(Element element:elements){
				  	//页面中的产品
				  if(!element.select("h3").text().isEmpty()){
					  link=element.select("a[target][class][href]").attr("href");						  
					  number=Integer.parseInt(element.select("span.sold-num").select("em").text());//只有购买人数和想购买人数在详细页面中读取不到
					  linkList.add(new LinkDetails(link,number));
				  }
				  	//页面中的品牌
				  else if(!element.select("a[title][href][target][asid]").text().isEmpty()){
					  String brandLink=element.select("a[title][href][target][asid]").attr("href");
					  Document docBrand=Jsoup.connect(brandLink).timeout(10000).get();
					  Elements elementsOfFloor1=docBrand.select("li[class][id]");
					  
					  	//爆款专区
					  for(Element elementOfFloor1:elementsOfFloor1){
						  link=elementOfFloor1.select("a[href]").first().attr("href");
						  number=getNumber(elementOfFloor1);//只有购买人数和想购买人数在详细页面中读取不到	
						  linkList.add(new LinkDetails(link,number));
					  }					  
					  	//其他专区
					  Elements elementsOfFloorOthers=docBrand.select("div[class^=l-floor]");
					  for(int i=1;i<elementsOfFloorOthers.size()-1;i++){
						  List<Attribute> tempAttributeList=elementsOfFloorOthers.get(i).attributes().asList();
						  String floorLink=tempAttributeList.get(tempAttributeList.size()-1).getValue();
						  Document docFloorOthers=Jsoup.connect(floorLink).timeout(10000).get();
						  Elements elementsOfFloori=docFloorOthers.select("li[class]");
						  	//对第i个专区中的所有商品进行循环
						  for(Element elementOfFloori:elementsOfFloori){
							  link=elementOfFloori.select("a[target][href]").first().attr("href");							  
							  number=getNumber(elementOfFloori);;//只有购买人数和想购买人数在详细页面中读取不到
							  linkList.add(new LinkDetails(link,number));
						  }							  
					  }
				  }
			  }
		  }catch(Exception e){
			  
		  }			
		return linkList;
	}
	public int getNumber(Element element){
		int number;
		String numberStr=element.select("span.sold-num").select("em").text();
		if(numberStr.equals("")){
			number=0;
		}else number=Integer.parseInt(numberStr);
		return number;
	}
}
