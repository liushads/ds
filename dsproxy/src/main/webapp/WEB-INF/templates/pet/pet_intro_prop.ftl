<#include "/include/header.ftl">
<#include "/include/pet.ftl">
<card title="${game_title}"><p>
【宠物属性说明】<br/>
 1、宠物拥有的属性在跟随状态下是加在人物属性上的（类似于穿上装备的效果）。宠物每升1级增加5点属性，5点属性随机分配到3个属性上。使用“血菩提”洗点，可以重新分配一次宠物的属性点。<br/>
 2、宠物属性加在人物身上的多少和饥饿度有直接关系：当饥饿度在401-500时，属性加成度为100%。当饥饿度为301-400时，属性加成度为80%，当饥饿度为201-300时，属性加成度为60%。当饥饿度为101-200时，属性加成度为30%。低于100时，属性加成度为0。<br/>
<@goback/>
<@goPet/>
</p></card>
</wml>