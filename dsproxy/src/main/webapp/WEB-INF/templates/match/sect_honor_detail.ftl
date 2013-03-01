<#include "/include/header.ftl">
<card title="${game_title}"><p>
<img alt="" src="${img_server}/${sect.imgName}"/><br/> 
【第${obj}届荣誉堂】<br/>
<#if sect_honor?size==0>
${match_type}:空缺<br/>
<#else>
<#list sect_honor as sh>
${match_type}:<@a href="/p?p_VO/${pid}/${sh.playerId}/"/>${sh.playerName}</a><br/>
</#list>
</#if>
<@goback/>
</p></card>
</wml>