<#include "/include/header.ftl">

<card title="${game_title}">
<p>
<img alt="" src="${img_server}/${sect.imgName}"/><br/>
${sect.name}：<br/>
${sect.intro}<br/>
<@a href="/p?se_J/${pid}/${sect.id}/"/>加入门派</a><br/>
<@goback/>
</p></card>
</wml>