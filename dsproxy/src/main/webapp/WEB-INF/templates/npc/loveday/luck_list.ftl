<#include "/include/header.ftl">
<card title="${game_title}"><p>
找有缘人<br/>
<#if targetm?exists>
哇！你的有缘人已经产生了！赶紧联络一下感情吧！<br/>
<@a href="/p?p_VO/${pid}/${targetm.id}/"/>${targetm.name}</a><br/>
<#else>
没有找到<br/>
</#if>
<@a href="/p?n_luck/${pid}/luck/"/>继续找有缘人</a><br/>
<@goback/>
<@gofacility/>
<@gogame/>
</p></card>
</wml>