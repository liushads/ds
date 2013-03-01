<#-- 显示可以合成的物品列表 -->
<#include "/include/header.ftl">
<card title="${game_title}"><p>
${location}<br/>
分解大师:学习分解术需要10张书页，和100银你准备好了吗？<br/>
<@a href="/p?n_itf/${pid}/d/l/0/"/>确定</a><br/>
<@goback/>
<@gofacility/>
<@gogame/>
</p>
</card>
</wml>