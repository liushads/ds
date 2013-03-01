<#include "/include/header.ftl">
<card title="${game_title}"><p>
${location}<br/>
你已选择目的地${city}，距离${distance}里<br/>
<@a href="/p?mv_G/${pid}/"/>出发</a><br/>
<@goback/>
<@gofacility/>
</p></card>
</wml>