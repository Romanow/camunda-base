package ru.romanow.camunda.utils

import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Tag
import org.camunda.bpm.engine.ProcessEngine
import org.camunda.bpm.engine.impl.event.EventType
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicLong

@Component
class CamundaMonitoringMetrics(registry: MeterRegistry, processEngine: ProcessEngine) {
    private final val processEngine: ProcessEngine
    private final val activeIncidents: AtomicLong
    private final val activeUserTasks: AtomicLong
    private final val activeMessageEventSubscriptions: AtomicLong
    private final val activeSignalEventSubscriptions: AtomicLong
    private final val activeCompensateEventSubscriptions: AtomicLong
    private final val activeConditionalEventSubscriptions: AtomicLong
    private final val executableJobs: AtomicLong
    private final val executableTimerJobs: AtomicLong
    private final val timerJobs: AtomicLong
    private final val messageJobs: AtomicLong
    private final val userCount: AtomicLong
    private final val tenantCount: AtomicLong
    private final val activeProcessInstances: AtomicLong
    private final val completedProcessInstances: AtomicLong
    private final val activeProcessDefinitions: AtomicLong
    private final val deployments: AtomicLong
    private final val activeExternalTasks: AtomicLong
    private final val activeLockedExternalTasks: AtomicLong
    private final val activeNotLockedExternalTasks: AtomicLong

    init {
        this.processEngine = processEngine
        val commonTags = listOf(Tag.of("engineName", processEngine.name))
        this.activeIncidents = registry.gauge(ACTIVE_INCIDENTS_METRIC_NAME, commonTags, AtomicLong(0))!!
        this.activeUserTasks = registry.gauge(ACTIVE_USER_TASKS_METRIC_NAME, commonTags, AtomicLong(0))!!
        this.activeMessageEventSubscriptions =
            registry.gauge(ACTIVE_MESSAGE_EVENT_SUBSCRIPTIONS_METRIC_NAME, commonTags, AtomicLong(0))!!
        this.activeSignalEventSubscriptions =
            registry.gauge(ACTIVE_SIGNAL_EVENT_SUBSCRIPTIONS_METRIC_NAME, commonTags, AtomicLong(0))!!
        this.activeCompensateEventSubscriptions =
            registry.gauge(ACTIVE_COMPENSATE_EVENT_SUBSCRIPTIONS_METRIC_NAME, commonTags, AtomicLong(0))!!
        this.activeConditionalEventSubscriptions =
            registry.gauge(ACTIVE_CONDITIONAL_EVENT_SUBSCRIPTIONS_METRIC_NAME, commonTags, AtomicLong(0))!!
        this.executableJobs = registry.gauge(EXECUTABLE_JOBS_METRIC_NAME, commonTags, AtomicLong(0))!!
        this.executableTimerJobs = registry.gauge(EXECUTABLE_TIMER_JOBS_METRIC_NAME, commonTags, AtomicLong(0))!!
        this.timerJobs = registry.gauge(TIMER_JOBS_METRIC_NAME, commonTags, AtomicLong(0))!!
        this.messageJobs = registry.gauge(MESSAGE_JOBS_METRIC_NAME, commonTags, AtomicLong(0))!!
        this.userCount = registry.gauge(USER_COUNT_METRIC_NAME, commonTags, AtomicLong(0))!!
        this.tenantCount = registry.gauge(TENANT_COUNT_METRIC_NAME, commonTags, AtomicLong(0))!!
        this.activeProcessInstances =
            registry.gauge(ACTIVE_PROCESS_INSTANCES_METRIC_NAME, commonTags, AtomicLong(0))!!
        this.completedProcessInstances =
            registry.gauge(COMPLETED_PROCESS_INSTANCES_METRIC_NAME, commonTags, AtomicLong(0))!!
        this.activeProcessDefinitions =
            registry.gauge(ACTIVE_PROCESS_DEFINITIONS_METRIC_NAME, commonTags, AtomicLong(0))!!
        this.deployments = registry.gauge(DEPLOYMENTS_METRIC_NAME, commonTags, AtomicLong(0))!!
        this.activeExternalTasks = registry.gauge(ACTIVE_EXTERNAL_TASKS_METRIC_NAME, commonTags, AtomicLong(0))!!
        this.activeLockedExternalTasks =
            registry.gauge(ACTIVE_LOCKED_EXTERNAL_TASKS_METRIC_NAME, commonTags, AtomicLong(0))!!
        this.activeNotLockedExternalTasks =
            registry.gauge(ACTIVE_NOT_LOCKED_EXTERNAL_TASKS_METRIC_NAME, commonTags, AtomicLong(0))!!
    }

    @Scheduled(fixedRate = MINUTE, timeUnit = TimeUnit.SECONDS)
    fun updateShortMetrics() {
        activeIncidents.set(
            processEngine.runtimeService
                .createIncidentQuery()
                .count()
        )
        activeUserTasks.set(
            processEngine.taskService
                .createTaskQuery()
                .active()
                .count()
        )
        activeMessageEventSubscriptions.set(
            processEngine.runtimeService
                .createEventSubscriptionQuery()
                .eventType(EventType.MESSAGE.name())
                .count()
        )
        activeSignalEventSubscriptions.set(
            processEngine.runtimeService
                .createEventSubscriptionQuery()
                .eventType(EventType.SIGNAL.name())
                .count()
        )
        activeCompensateEventSubscriptions.set(
            processEngine.runtimeService
                .createEventSubscriptionQuery()
                .eventType(EventType.COMPENSATE.name())
                .count()
        )
        activeConditionalEventSubscriptions.set(
            processEngine.runtimeService
                .createEventSubscriptionQuery()
                .eventType(EventType.CONDITONAL.name())
                .count()
        )
        executableJobs.set(
            processEngine.managementService
                .createJobQuery()
                .executable()
                .count()
        )
        executableTimerJobs.set(
            processEngine.managementService
                .createJobQuery()
                .executable()
                .timers()
                .count()
        )
        timerJobs.set(
            processEngine.managementService
                .createJobQuery()
                .timers()
                .count()
        )
        messageJobs.set(
            processEngine.managementService
                .createJobQuery()
                .messages()
                .count()
        )
        activeProcessInstances.set(
            processEngine.runtimeService
                .createProcessInstanceQuery()
                .active()
                .count()
        )
        activeProcessDefinitions.set(
            processEngine.repositoryService
                .createProcessDefinitionQuery()
                .active()
                .count()
        )
        deployments.set(
            processEngine.repositoryService
                .createDeploymentQuery()
                .count()
        )
        activeExternalTasks.set(
            processEngine.externalTaskService
                .createExternalTaskQuery()
                .active()
                .count()
        )
        activeLockedExternalTasks.set(
            processEngine.externalTaskService
                .createExternalTaskQuery()
                .active()
                .locked()
                .count()
        )
        activeNotLockedExternalTasks.set(
            processEngine.externalTaskService
                .createExternalTaskQuery()
                .active()
                .notLocked()
                .count()
        )
    }

    @Scheduled(fixedRate = HOUR, timeUnit = TimeUnit.SECONDS)
    fun updateLongMetrics() {
        tenantCount.set(
            processEngine.identityService
                .createTenantQuery()
                .count()
        )
        userCount.set(
            processEngine.identityService
                .createUserQuery()
                .count()
        )
        completedProcessInstances.set(
            processEngine.historyService
                .createHistoricProcessInstanceQuery()
                .completed()
                .count()
        )
    }

    companion object {
        private const val HOUR: Long = 3600
        private const val MINUTE: Long = 60

        private const val ACTIVE_INCIDENTS_METRIC_NAME = "camunda_active_incidents"
        private const val ACTIVE_USER_TASKS_METRIC_NAME = "camunda_active_user_tasks"
        private const val ACTIVE_MESSAGE_EVENT_SUBSCRIPTIONS_METRIC_NAME = "camunda_active_message_event_subscriptions"
        private const val ACTIVE_SIGNAL_EVENT_SUBSCRIPTIONS_METRIC_NAME = "camunda_active_signal_event_subscriptions"
        private const val ACTIVE_COMPENSATE_EVENT_SUBSCRIPTIONS_METRIC_NAME =
            "camunda_active_compensate_event_subscriptions"
        private const val ACTIVE_CONDITIONAL_EVENT_SUBSCRIPTIONS_METRIC_NAME =
            "camunda_active_conditional_event_subscriptions"
        private const val EXECUTABLE_JOBS_METRIC_NAME = "camunda_executable_jobs"
        private const val EXECUTABLE_TIMER_JOBS_METRIC_NAME = "camunda_executable_timer_jobs"
        private const val TIMER_JOBS_METRIC_NAME = "camunda_timer_jobs"
        private const val MESSAGE_JOBS_METRIC_NAME = "camunda_message_jobs"
        private const val USER_COUNT_METRIC_NAME = "camunda_user_count"
        private const val TENANT_COUNT_METRIC_NAME = "camunda_tenant_count"
        private const val ACTIVE_PROCESS_INSTANCES_METRIC_NAME = "camunda_active_process_instances"
        private const val COMPLETED_PROCESS_INSTANCES_METRIC_NAME = "camunda_completed_process_instances"
        private const val ACTIVE_PROCESS_DEFINITIONS_METRIC_NAME = "camunda_active_process_definitions"
        private const val DEPLOYMENTS_METRIC_NAME = "camunda_deployments"
        private const val ACTIVE_EXTERNAL_TASKS_METRIC_NAME = "camunda_active_external_tasks"
        private const val ACTIVE_LOCKED_EXTERNAL_TASKS_METRIC_NAME = "camunda_active_locked_external_tasks"
        private const val ACTIVE_NOT_LOCKED_EXTERNAL_TASKS_METRIC_NAME = "camunda_active_not_locked_external_tasks"
    }
}
