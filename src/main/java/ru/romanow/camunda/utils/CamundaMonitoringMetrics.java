package ru.romanow.camunda.utils;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.event.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.concurrent.TimeUnit.SECONDS;
import static ru.romanow.camunda.utils.MetricsNames.*;

@Component
public class CamundaMonitoringMetrics {

    private static final long HOUR = 3600;
    private static final long MINUTE = 60;

    private final ProcessEngine processEngine;
    private final AtomicLong activeIncidents;
    private final AtomicLong activeUserTasks;
    private final AtomicLong activeMessageEventSubscriptions;
    private final AtomicLong activeSignalEventSubscriptions;
    private final AtomicLong activeCompensateEventSubscriptions;
    private final AtomicLong activeConditionalEventSubscriptions;
    private final AtomicLong executableJobs;
    private final AtomicLong executableTimerJobs;
    private final AtomicLong timerJobs;
    private final AtomicLong messageJobs;
    private final AtomicLong userCount;
    private final AtomicLong tenantCount;
    private final AtomicLong activeProcessInstances;
    private final AtomicLong completedProcessInstances;
    private final AtomicLong activeProcessDefinitions;
    private final AtomicLong deployments;
    private final AtomicLong activeExternalTasks;
    private final AtomicLong activeLockedExternalTasks;
    private final AtomicLong activeNotLockedExternalTasks;

    @Autowired
    public CamundaMonitoringMetrics(MeterRegistry registry, ProcessEngine processEngine) {
        this.processEngine = processEngine;

        final var commonTags = List.of(Tag.of("engineName", processEngine.getName()));
        this.activeIncidents = registry.gauge(ACTIVE_INCIDENTS_METRIC_NAME, commonTags, new AtomicLong(0));
        this.activeUserTasks = registry.gauge(ACTIVE_USER_TASKS_METRIC_NAME, commonTags, new AtomicLong(0));
        this.activeMessageEventSubscriptions = registry.gauge(ACTIVE_MESSAGE_EVENT_SUBSCRIPTIONS_METRIC_NAME, commonTags, new AtomicLong(0));
        this.activeSignalEventSubscriptions = registry.gauge(ACTIVE_SIGNAL_EVENT_SUBSCRIPTIONS_METRIC_NAME, commonTags, new AtomicLong(0));
        this.activeCompensateEventSubscriptions = registry.gauge(ACTIVE_COMPENSATE_EVENT_SUBSCRIPTIONS_METRIC_NAME, commonTags, new AtomicLong(0));
        this.activeConditionalEventSubscriptions = registry.gauge(ACTIVE_CONDITIONAL_EVENT_SUBSCRIPTIONS_METRIC_NAME, commonTags, new AtomicLong(0));
        this.executableJobs = registry.gauge(EXECUTABLE_JOBS_METRIC_NAME, commonTags, new AtomicLong(0));
        this.executableTimerJobs = registry.gauge(EXECUTABLE_TIMER_JOBS_METRIC_NAME, commonTags, new AtomicLong(0));
        this.timerJobs = registry.gauge(TIMER_JOBS_METRIC_NAME, commonTags, new AtomicLong(0));
        this.messageJobs = registry.gauge(MESSAGE_JOBS_METRIC_NAME, commonTags, new AtomicLong(0));
        this.userCount = registry.gauge(USER_COUNT_METRIC_NAME, commonTags, new AtomicLong(0));
        this.tenantCount = registry.gauge(TENANT_COUNT_METRIC_NAME, commonTags, new AtomicLong(0));
        this.activeProcessInstances = registry.gauge(ACTIVE_PROCESS_INSTANCES_METRIC_NAME, commonTags, new AtomicLong(0));
        this.completedProcessInstances = registry.gauge(COMPLETED_PROCESS_INSTANCES_METRIC_NAME, commonTags, new AtomicLong(0));
        this.activeProcessDefinitions = registry.gauge(ACTIVE_PROCESS_DEFINITIONS_METRIC_NAME, commonTags, new AtomicLong(0));
        this.deployments = registry.gauge(DEPLOYMENTS_METRIC_NAME, commonTags, new AtomicLong(0));
        this.activeExternalTasks = registry.gauge(ACTIVE_EXTERNAL_TASKS_METRIC_NAME, commonTags, new AtomicLong(0));
        this.activeLockedExternalTasks = registry.gauge(ACTIVE_LOCKED_EXTERNAL_TASKS_METRIC_NAME, commonTags, new AtomicLong(0));
        this.activeNotLockedExternalTasks = registry.gauge(ACTIVE_NOT_LOCKED_EXTERNAL_TASKS_METRIC_NAME, commonTags, new AtomicLong(0));
    }

    @Scheduled(fixedRate = MINUTE, timeUnit = SECONDS)
    void getActiveIncidents() {
        activeIncidents.set(processEngine.getRuntimeService()
                                         .createIncidentQuery()
                                         .count());
    }

    @Scheduled(fixedRate = MINUTE, timeUnit = SECONDS)
    void getActiveUserTasks() {
        activeUserTasks.set(processEngine.getTaskService()
                                         .createTaskQuery()
                                         .active()
                                         .count());
    }

    @Scheduled(fixedRate = MINUTE, timeUnit = SECONDS)
    void getActiveMessageEventSubscriptions() {
        activeMessageEventSubscriptions.set(processEngine.getRuntimeService()
                                                         .createEventSubscriptionQuery()
                                                         .eventType(EventType.MESSAGE.name())
                                                         .count());
    }

    @Scheduled(fixedRate = MINUTE, timeUnit = SECONDS)
    void getActiveSignalEventSubscriptions() {
        activeSignalEventSubscriptions.set(processEngine.getRuntimeService()
                                                        .createEventSubscriptionQuery()
                                                        .eventType(EventType.SIGNAL.name())
                                                        .count());
    }

    @Scheduled(fixedRate = MINUTE, timeUnit = SECONDS)
    void getActiveCompensateEventSubscriptions() {
        activeCompensateEventSubscriptions.set(processEngine.getRuntimeService()
                                                            .createEventSubscriptionQuery()
                                                            .eventType(EventType.COMPENSATE.name())
                                                            .count());
    }

    @Scheduled(fixedRate = MINUTE, timeUnit = SECONDS)
    void getActiveConditionalEventSubscriptions() {
        activeConditionalEventSubscriptions.set(processEngine.getRuntimeService()
                                                             .createEventSubscriptionQuery()
                                                             .eventType(EventType.CONDITONAL.name())
                                                             .count());
    }

    @Scheduled(fixedRate = MINUTE, timeUnit = SECONDS)
    void getExecutableJobs() {
        executableJobs.set(processEngine.getManagementService()
                                        .createJobQuery()
                                        .executable()
                                        .count());
    }

    @Scheduled(fixedRate = MINUTE, timeUnit = SECONDS)
    void getExecutableTimerJobs() {
        executableTimerJobs.set(processEngine.getManagementService()
                                             .createJobQuery()
                                             .executable()
                                             .timers()
                                             .count());
    }

    @Scheduled(fixedRate = MINUTE, timeUnit = SECONDS)
    void getTimerJobs() {
        timerJobs.set(processEngine.getManagementService()
                                   .createJobQuery()
                                   .timers()
                                   .count());
    }

    @Scheduled(fixedRate = MINUTE, timeUnit = SECONDS)
    void getMessageJobs() {
        messageJobs.set(processEngine.getManagementService()
                                     .createJobQuery()
                                     .messages()
                                     .count());
    }

    @Scheduled(fixedRate = HOUR, timeUnit = SECONDS)
    void getUserCount() {
        userCount.set(processEngine.getIdentityService()
                                   .createUserQuery()
                                   .count());
    }

    @Scheduled(fixedRate = HOUR)
    void getTenantCount() {
        tenantCount.set(processEngine.getIdentityService()
                                     .createTenantQuery()
                                     .count());
    }

    @Scheduled(fixedRate = MINUTE, timeUnit = SECONDS)
    void getActiveProcessInstances() {
        activeProcessInstances.set(processEngine.getRuntimeService()
                                                .createProcessInstanceQuery()
                                                .active()
                                                .count());
    }

    @Scheduled(fixedRate = HOUR, timeUnit = SECONDS)
    void getCompletedProcessInstances() {
        completedProcessInstances.set(processEngine.getHistoryService()
                                                   .createHistoricProcessInstanceQuery()
                                                   .completed()
                                                   .count());
    }

    @Scheduled(fixedRate = MINUTE, timeUnit = SECONDS)
    void getActiveProcessDefinitions() {
        activeProcessDefinitions.set(processEngine.getRepositoryService()
                                                  .createProcessDefinitionQuery()
                                                  .active()
                                                  .count());
    }

    @Scheduled(fixedRate = MINUTE, timeUnit = SECONDS)
    void getDeployments() {
        deployments.set(processEngine.getRepositoryService()
                                     .createDeploymentQuery()
                                     .count());
    }

    @Scheduled(fixedRate = MINUTE, timeUnit = SECONDS)
    void getActiveExternalTasks() {
        activeExternalTasks.set(processEngine.getExternalTaskService()
                                             .createExternalTaskQuery()
                                             .active()
                                             .count());
    }

    @Scheduled(fixedRate = MINUTE, timeUnit = SECONDS)
    void getActiveLockedExternalTasks() {
        activeLockedExternalTasks.set(processEngine.getExternalTaskService()
                                                   .createExternalTaskQuery()
                                                   .active()
                                                   .locked()
                                                   .count());
    }

    @Scheduled(fixedRate = MINUTE, timeUnit = SECONDS)
    void getActiveNotLockedExternalTasks() {
        activeNotLockedExternalTasks.set(processEngine.getExternalTaskService()
                                                      .createExternalTaskQuery()
                                                      .active()
                                                      .notLocked()
                                                      .count());
    }
}