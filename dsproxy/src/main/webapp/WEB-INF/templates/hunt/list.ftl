<#include "/include/header.ftl">
<card title="${game_title}"><p>
${location}<br/>
<#include "rewards.ftl">
<#assign items=label_missions?default(ls)>
<@itemlist mylist=items />
<br/><@gofacility/>
</p>
</card>
</wml>

 