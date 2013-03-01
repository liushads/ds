<#include "/include/header.ftl">
<card title="${game_title}"><p>
<img alt="" src="${img_server}/${sect.imgName}"/><br/> 
【历届荣誉堂】<br/>
<#if objs?size==0>
暂无<br/>
<#else>
<#list objs as seqno>
<@a href="/p?mc_SH/${pid}/${sect.id}/${seqno}/"/>第${seqno}届荣誉堂</a><br/>
</#list>
</#if>
<@goback/>
</p></card>
</wml>