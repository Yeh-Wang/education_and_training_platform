import {createRouter, createWebHistory, RouteRecordRaw} from 'vue-router'
import loginView from "@/views/LoginView.vue";

const routes: Array<RouteRecordRaw> = [
    {
        path: '/',
        name: 'login',
        component: loginView
    },
    {
        path: '/orgAdm/:id',
        name: 'organization_administrator',
        component: () => import('../views/OrgAdmView.vue'),
        children: [
            {
                path: '/orgInfoManage/:id/:adm_id',
                name: 'organization_info_manage',
                component: () => import('../components/orgInfoManage.vue')
            },
            {
                path: '/stuInfoManage/:id',
                name: 'student_info_manage',
                component: () => import('../components/stuInfoManage.vue')
            },
            {
                path: '/orgTrainingManage/:id/:adm_id',
                name: 'organization_training_manage',
                component: () => import('../components/orgTrainingManage.vue')
            },
            {
                path: '/planResourceConf/:id/:adm_id',
                name: 'plan_resource_conf',
                component: () => import('../components/planResourceConf.vue'),
            }
        ]
    },
    {
        path: '/superAdm/:id',
        name: 'super_adm',
        component: () => import('../views/SuperAdmView.vue'),
        children: [
            {
                path: '/allOrgInfoManage',
                name: 'all_organization_info_manage',
                component: () => import('../components/superAdm/allOrgManage.vue')
            },
            {
                path: '/allUserInfoManage',
                name: 'all_user_info_manage',
                component: () => import('../components/superAdm/allUserInfoManage.vue')
            },
            {
                path: '/allPlanInfoManage',
                name: 'all_plan_info_manage',
                component: () => import('../components/superAdm/allPlanInfoManage.vue')
            },
            {
                path: '/allResourceManage',
                name: 'all_resource_manage',
                component: () => import('../components/superAdm/allResourceManage.vue')
            }
        ]
    },
    {
        path: '/regularUser/:id',
        name: 'regular_adm',
        component: () => import('../views/RegularUserView.vue'),
        children: [
            {
                path: '/planInfoView/:plan_id',
                name: 'plan_info_view',
                component: () => import('../components/regularUser/planInfoView.vue')
            },
            {
                path: '/userExamView/:user_id/:plan_id',
                name: 'user_exam_view',
                component: () => import('../components/regularUser/userExamView.vue')
            },
            {
                path: '/planResourceView/:user_id/:plan_id',
                name: 'plan_resource_view',
                component: () => import('../components/regularUser/planResourceView.vue')
            },
            {
                path: '/personalInfoView/:user_id/:plan_id',
                name: 'personal_info_view',
                component: () => import('../components/regularUser/personalInfoView.vue')
            }
        ]
    },
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router
