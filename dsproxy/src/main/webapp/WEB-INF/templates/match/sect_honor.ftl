<#include "/include/header.ftl">
<card title="${game_title}"><p>
<img alt="" src="${img_server}/${sect.imgName}"/><br/> 
<#if obj == 0>
【荣誉堂】<br/>
暂无<br/>
<#else>
【第${obj}届荣誉堂】<br/>
<#if sect_honor?size==0>
长老:空缺<br/>
<#else>
<#list sect_honor as sh>
长老:<@a href="/p?p_VO/${pid}/${sh.playerId}/"/>${sh.playerName}</a><br/>
</#list>
</#if>
<@a href="/p?mc_SHD/${pid}/${sect.id}/${obj}/8"/>尊者</a><br/>
<@a href="/p?mc_SHD/${pid}/${sect.id}/${obj}/7"/>护法</a><br/>
<@a href="/p?mc_SHD/${pid}/${sect.id}/${obj}/6"/>使者</a><br/>
<@a href="/p?mc_SHL/${pid}/${sect.id}/"/>历届荣誉</a><br/>
</#if>
<@goback/>
<@gofacility/>
</p></card>
</wml>