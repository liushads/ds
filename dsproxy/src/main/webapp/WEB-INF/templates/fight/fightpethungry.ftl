<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}">
<p>
您的宠物【${playerPet.customName}】现在饥饿度为${hungry}，对你的属性加成度为${hungryPercent}%<br/>
<@a href="/p?pe_FP/${pid}/${playerPet.id}"/>喂养</a><br/>
<@gofacility/>
<br/>
</p>
</card>
</wml>