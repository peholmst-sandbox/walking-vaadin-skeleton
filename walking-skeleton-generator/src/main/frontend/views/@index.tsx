import { Button, Notification, RadioButton, RadioGroup, TextField } from '@vaadin/react-components';
import { useSignal, useSignalEffect } from '@vaadin/hilla-react-signals';

type UiFramework = 'FLOW' | 'REACT'

const getContextPath = (): string => {
  return document.querySelector('meta[name="context-path"]')?.getAttribute('content') || '';
};

const buildUrl = (path: string): string => {
  const contextPath = getContextPath();
  const cleanPath = path.startsWith('/') ? path.substring(1) : path;
  return `${contextPath}/${cleanPath}`;
};

export default function ProjectGeneratorView() {
  const groupId = useSignal('');
  const artifactId = useSignal('');
  const basePackage = useSignal('');
  const uiFramework = useSignal<UiFramework>('FLOW');
  const error = useSignal(false);
  const requiredDataMissing = useSignal(true);

  // TODO Should also validate the input of the fields
  useSignalEffect(() => {
    requiredDataMissing.value = groupId.value.length == 0
      || artifactId.value.length == 0
      || basePackage.value.length == 0;
  });

  const downloadProject = async () => {
    error.value = false;

    const projectConfiguration = {
      artifactId: artifactId.value,
      groupId: groupId.value,
      basePackage: basePackage.value,
      uiFramework: uiFramework.value
    };

    const requestOptions = {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(projectConfiguration)
    };

    const response = await fetch(buildUrl('/generate'), requestOptions);

    if (!response.ok) {
      error.value = true;
      return;
    }

    const blob = await response.blob();
    await downloadFile(blob, projectConfiguration.artifactId);
  };

  const downloadFile = async (blob: Blob, artifactId: string) => {
    const url = window.URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.href = url;
    link.setAttribute('download', `${artifactId}.zip`);
    document.body.appendChild(link);
    link.click();
    link.remove();
    window.URL.revokeObjectURL(url);
  };

  return (
    <div className="w-full h-full flex justify-center items-center bg-contrast-10">
      <div className="bg-base shadow-m p-l rounded-l" style={{ 'max-width': '550px' }}>
        <h1>Walking Skeleton Generator</h1>
        <p>
          Please fill in the following fields to create your new Vaadin project:
        </p>
        <div className="flex flex-col">
          <TextField label="Maven Project Group ID" placeholder="Example: com.example.application"
                     value={groupId.value}
                     onValueChanged={evt => groupId.value = evt.detail.value}></TextField>
          <TextField label="Maven Project Artifact ID" placeholder="Example: my-project"
                     value={artifactId.value}
                     onValueChanged={evt => artifactId.value = evt.detail.value}></TextField>
          <TextField label="Java Base Package" placeholder="Example: com.example.application"
                     value={basePackage.value}
                     onValueChanged={evt => basePackage.value = evt.detail.value}></TextField>
          <RadioGroup label="User Interface Framework" value={uiFramework.value}
                      onValueChanged={evt => uiFramework.value = evt.detail.value as UiFramework}>
            <RadioButton value="FLOW" label="Flow" />
            <RadioButton value="REACT" label="React" />
          </RadioGroup>
          <div className="text-secondary text-s my-l">
            The generated project is configured to use <b>Java 21</b>. Unzip it, and start the application by running
            the command: <code>./mvnw
            spring-boot:run</code>
          </div>
          <Button disabled={requiredDataMissing.value} theme="primary" onClick={downloadProject}>Download
            Project</Button>
          <Notification opened={error.value} theme="error" position="top-center" onClosed={evt => error.value = false}>
            Whops, this did not go as planned. Please try again later.
          </Notification>
        </div>
      </div>
    </div>
  );
}